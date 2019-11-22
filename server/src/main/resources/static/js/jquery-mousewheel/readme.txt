1，插件介绍
jquery.mousewheel.js 是一个用于添加跨浏览器的鼠标滚轮支持的 jQuery 插件。
GitHub主页：https://github.com/jquery/jquery-mousewheel

2，使用说明
（1）使用该插件，只需将 mousewheel 事件绑定到一个元素上即可。当然也可以使用类似 jQuery 中其他的事件方法写法。下面列出这两种方式：
// 方式1：using on
$('#my_elem').on('mousewheel', function(event) {
    console.log(event.deltaX, event.deltaY, event.deltaFactor);
});
 
// 方式2：using the event helper
$('#my_elem').mousewheel(function(event) {
    console.log(event.deltaX, event.deltaY, event.deltaFactor);
});

（2）事件对象中可以获取如下三个属性值：
deltaX：值为负的（-1），则表示滚轮向左滚动。值为正的（1），则表示滚轮向右滚动。
deltaY：值为负的（-1），则表示滚轮向下滚动。值为正的（1），则表示滚轮向上滚动。
deltaFactor：增量因子。通过 deltaFactor * deltaX 或者 deltaFactor * deltaY 可以得到浏览器实际的滚动距离。

（3）如果想要对整个窗口进行滚轮事件监听，可以将监听添加在 window 上。

$(window).mousewheel(function(event) {
   console.log(event.deltaX, event.deltaY, event.deltaFactor);
});