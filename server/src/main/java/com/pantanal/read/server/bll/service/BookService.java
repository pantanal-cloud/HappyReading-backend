package com.pantanal.read.server.bll.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.bean.BookChapterBean;
import com.pantanal.read.common.bean.BookTypeBean;
import com.pantanal.read.common.bean.ChannelBean;
import com.pantanal.read.common.dao.BookChapterDao;
import com.pantanal.read.common.dao.BookDao;
import com.pantanal.read.common.dao.BookTypeDao;
import com.pantanal.read.common.dao.ChannelDao;
import com.pantanal.read.common.util.JsonUtil;
import com.pantanal.read.server.common.SysCfg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Slf4j
@Service
public class BookService {
  @Resource
  private BookDao bookDao;
  @Resource
  private ChannelDao channelDao;
  @Resource
  private BookTypeDao bookTypeDao;
  @Resource
  private BookChapterDao bookChapterDao;

  @Scheduled(cron = "0 30 0 * * ?")
  public void importBook() {
    log.info("====开始导入图书====");
    int imported = 0;
    if (StringUtils.isNotBlank(SysCfg.get().getBookImportDir())) {
      File dirFile = new File(SysCfg.get().getBookImportDir());
      String doneDir = dirFile.getParent() + "/done";
      if (dirFile.exists() && dirFile.isDirectory()) {
        File[] files = dirFile.listFiles();
        if (files != null) {
          File file = null;
          List<String> lines;
          try {

            for (int i = 0; i < files.length; i++) {
              file = files[i];
              lines = FileUtils.readLines(file);
              for (String line : lines) {
                if (line.length() <= 32) {
                  continue;
                }
                line = line.substring(32);
                JsonNode jsonNode = JsonUtil.stringToJson(line);
                if (jsonNode == null) {
                  continue;
                }
                String libtype = jsonNode.get("libtype").asText();
                if ("www.3gsc.com.cn".equals(libtype)) {
                  imported += handlerGgg(jsonNode);
                }
              }
              FileUtils.moveFileToDirectory(file, new File(doneDir), true);
            }
          } catch (IOException e) {
            log.error("read line error, file: {}", file, e);
          }
        }
      }
    }
    log.info("====导入图书完成 {} 本====", imported);
  }

  protected int handlerGgg(JsonNode jsonNode) {
    String detailUrl, coverurl, title, author, intro, state, channel, type, free, words, clicks, recommends;
    JsonNode dataobject = jsonNode.get("dataobject");
    JsonNode extlibinfo = jsonNode.get("extlibinfo");
    JsonNode coverinfo = jsonNode.get("coverinfo");
    JsonNode contents = jsonNode.get("contents");


    title = dataobject.get("题名").asText(null);
    author = dataobject.get("作者").asText("佚名");
    intro = dataobject.get("内容简介").asText(null);
    channel = dataobject.get("分类").asText(null);
    state = dataobject.get("状态").asText(null);
    type = dataobject.get("类别").asText(null);
    words = dataobject.get("字数").asText(null);
    clicks = dataobject.get("总点击").asText(null);
    recommends = dataobject.get("总推荐").asText(null);
    coverurl = coverinfo.get("coverurl").asText(null);
    detailUrl = extlibinfo.get("detailurl").asText(null);

    if (StringUtils.isBlank(title)) {
      return 0;
    }

    ChannelBean channelBean = channelDao.selectOne(Wrappers.<ChannelBean>lambdaQuery().eq(ChannelBean::getName, channel));
    if (channelBean == null) {
      channelBean = new ChannelBean();
      channelBean.setName(channel);
      channelBean.setSelectedStatus(1);
      channelBean.setType("books");
      channelDao.insert(channelBean);
    }

    BookTypeBean bookTypeBean = bookTypeDao.selectOne(Wrappers.<BookTypeBean>lambdaQuery().eq(BookTypeBean::getName, type));
    if (bookTypeBean == null) {
      bookTypeBean = new BookTypeBean();
      bookTypeBean.setName(type);
      bookTypeBean.setSelectedStatus(1);
      bookTypeDao.insert(bookTypeBean);
    }

    BookBean bookBean = null;
    if (StringUtils.isNotBlank(detailUrl)) {
      bookBean = bookDao.selectOne(Wrappers.<BookBean>lambdaQuery().eq(BookBean::getUrl, detailUrl));
    } else {
      bookBean = bookDao.selectOne(Wrappers.<BookBean>lambdaQuery().eq(BookBean::getName, title).eq(BookBean::getAuthor, author));
    }
    if (bookBean == null) {
      bookBean = new BookBean();
      bookBean.setName(title);
      bookBean.setAuthor(author);
      bookBean.setIntro(intro);
      bookBean.setTypeId(bookTypeBean.getId());
      bookBean.setTypeName(type);
      bookBean.setUrl(detailUrl);
      bookBean.setIsFinished(state.indexOf("连载") < 0);
      bookBean.setWordNum((int) NumberUtils.toDouble(words));
      bookBean.setClickNum(NumberUtils.toInt(clicks));
      bookBean.setRecommendNum(NumberUtils.toInt(recommends));
      bookBean.setCoverUrl(coverurl);
      bookBean.setChannelIds(new Long[]{channelBean.getId()});
      bookBean.setCreateTime(LocalDateTime.now());
      bookDao.insert(bookBean);
    }


    BookChapterBean lastChapterBean = null;
    int chapters = 0;
    if (contents.isArray()) {
      ArrayNode contentsNode = (ArrayNode) contents;
      chapters = contentsNode.size();
      BookChapterBean bookChapterBean;
      String chapterUrl, chapterIndex, chapterName, chapterFree;
      JsonNode chapterNode;
      for (int i = 0; i < contentsNode.size(); i++) {
        chapterNode = contentsNode.get(i);
        chapterUrl = chapterNode.get("url").asText(null);
        chapterFree = chapterNode.get("free").asText("true");
        chapterIndex = chapterNode.get("index").asText((i + 1) + "");
        chapterName = chapterNode.get("text").asText(null);

        if (StringUtils.isNotBlank(chapterUrl)) {
          bookChapterBean = bookChapterDao.selectOne(Wrappers.<BookChapterBean>lambdaQuery().eq(BookChapterBean::getUrl, chapterUrl));
        } else {
          bookChapterBean = bookChapterDao.selectOne(Wrappers.<BookChapterBean>lambdaQuery().eq(BookChapterBean::getBookId, bookBean.getId()).eq(BookChapterBean::getIndex, chapterIndex));
        }
        if (bookChapterBean == null) {
          bookChapterBean = new BookChapterBean();
          bookChapterBean.setBookId(bookBean.getId());
          bookChapterBean.setUrl(chapterUrl);
          bookChapterBean.setIndex(NumberUtils.toInt(chapterIndex));
          bookChapterBean.setVip(!BooleanUtils.toBoolean(chapterFree));
          bookChapterBean.setCreateTime(LocalDateTime.now());
          bookChapterBean.setName(chapterName);
          bookChapterDao.insert(bookChapterBean);
        } else {
          bookChapterBean.setBookId(bookBean.getId());
          bookChapterBean.setUrl(chapterUrl);
          bookChapterBean.setIndex(NumberUtils.toInt(chapterIndex));
          bookChapterBean.setVip(!BooleanUtils.toBoolean(chapterFree));
          if (StringUtils.isNotBlank(chapterName))
            bookChapterBean.setName(chapterName);
          bookChapterDao.updateById(bookChapterBean);
        }
        if (i == contentsNode.size() - 1) {
          lastChapterBean = bookChapterBean;
        }
      }
    }

    bookBean.setName(title);
    bookBean.setAuthor(author);
    bookBean.setIntro(intro);
    bookBean.setTypeId(bookTypeBean.getId());
    bookBean.setTypeName(type);
    bookBean.setUrl(detailUrl);
    bookBean.setIsFinished(state.indexOf("连载") < 0);
    bookBean.setWordNum((int) NumberUtils.toDouble(words));
    bookBean.setClickNum(NumberUtils.toInt(clicks));
    bookBean.setRecommendNum(NumberUtils.toInt(recommends));
    bookBean.setCoverUrl(coverurl);
    bookBean.setChapterCount(chapters);
    bookBean.setLastChapterId(lastChapterBean == null ? null : lastChapterBean.getId());
    Set<Long> channerSet = new HashSet();
    if (bookBean.getChannelIds() != null) {
      Collections.addAll(channerSet, bookBean.getChannelIds());
    }
    Collections.addAll(channerSet, channelBean.getId());
    bookBean.setChannelIds(channerSet.toArray(new Long[0]));

    bookDao.updateById(bookBean);
    return 1;
  }
}
