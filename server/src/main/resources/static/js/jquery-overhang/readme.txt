插件描述： overhang.js 是一个JQuery插件显示即时通知、 确认或给定元素中的提示。

GitHub主页：https://github.com/paulkr/overhang.js


使用方法
引用了最新版本的jQuery和jQuery UI。

<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/jqueryui/1.12.0/jquery-ui.js"></script>
引用的JavaScript和CSS文件。

<link rel="stylesheet" type="text/css" href="dist/overhang.min.css" />
<script type="text/javascript" src="dist/overhang.min.js"></script>
配置参数
overhang.js有3个主要特点-通知、提示和确认。大多数的选择是定制所有这些特征。

默认值

type

这是您想要显示通知的类型。预设的类型都是成功，错误，警告信息，提示和确认。

如果你想使用一个自定义的主题，离开这个参数的空白，按照自定义的主题设置的规则。

$("body").overhang({
   custom: true, 
   primary: "#34495E", 
   accent: "#F4B350" 
});
primary - 警报的背景颜色

accent - 底边框颜色

如果你想显示一个提示或确认警报，设置类型促使或确认，分别。提示和确认都有预设的主题，但你可以自定义他们利用自定义选项

textColor

文本的颜色。默认设置为白色。

message

要在您的通知中显示的消息。

duration

以秒为单位显示的警报持续时间。默认值为 1.5 秒。

speed

下降速度，并提高警报以毫秒为单位。默认设置为500。

closeConfirm

设置为true，用户点击关闭警报，而不是它自动消失。

upper

设置为 true，如果你想要你所有字母均为大写的消息。默认值设置为 false。

easing

JQuery UI 缓动的效果的选项。默认设置为"easeOutBounce"

html

这是一个布尔值，如果消息参数应被解释为 HTML。默认值设置为 false。

基本的警报通知的例子

// Some error notification
$("body").overhang({
   type: "error",
   message: "You could not be logged in at this time.",
   closeConfirm: "true"
});
提示
当使用提示，所有你需要做的是设置类型参数"prompt"

提示的例子

// Some prompt notification
$("body").overhang({
   type: "prompt",
   message: "What is your name"
});
确认
在使用时确认，有您可以自定义的附加选项。

yesMessage

这是将显示"true"按钮上的文本。默认设置为"Yes"。

noMessage

这是将显示"false"按钮上的文本。默认设置为"No"。

yesColor

这是按钮的"true"的颜色。默认设置为"#2ECC71"。

noColor

这是"false"按钮的颜色。默认设置为"#E74C3C"。

验证实例

// Some confirmation
$("body").overhang({
   type: "confirm",
   yesMessage: "Yes please!",
   noMessage: "No thanks."
});
检索数据
提示和确认的功能都允许您从用户获取数据。回复存储作为目标元素的 DOM 中的数据。

要检索的数据应使用 jQuery 像这样┱

// From a prompt
alert($("target-element").data("overhangPrompt"));
// From a confirmation (either true or false)
alert($("target-element").data("overhangConfirm"));
如果用户还未予以响应，默认值将被设置为 null。

回调
该选项回调参数是一个函数，一旦用户上悬通知进行了操作。在任何这些情况后将运行回调┱

提交的提示

确认选择

与真正的closeConfirm 的正常通知关闭按钮

正常的通知的提高

注┱对于确认或提示，回调将不运行时单击关闭按钮，并且没有选定。

例子

$("body").overhang({
    type: "confirm",
    message: "Are you sure?",
  // This code will run once an option is clicked.
    callback: function () {
        var selection = $("body").data("overhangConfirm");
        alert("You made your selection of " + selection);
    }
});


=====================================================================================================================

Usage
Include a reference to the latest version of jQuery and jQuery UI. — The specific jQuery UI components required are: "effect.js" (Effects Core)

<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
Include references to the Javascript and CSS files.

<link rel="stylesheet" type="text/css" href="dist/overhang.min.css" />
<script type="text/javascript" src="dist/overhang.min.js"></script>
Configuration Parameters
overhang.js has 3 primary features - notifications, prompts and confirmations. Most of the options are customizable for all of these features.

Defaults
type
This is the type of the notification that you want to display. The preset types are success, error, warn, info, prompt and confirm.

If you would like to use a custom theme, leave this parameter blank and follow the rules for setting a custom theme.

$("body").overhang({
  custom: true, // Set custom to true
  primary: "#34495E", // Your custom primary color
  accent: "#F4B350" // Your custom accent color
});
primary - The background color of the alert.

accent - The bottom border color.

If you want to display either a prompt or confirmation alert, set the type to prompt or confirm, respectively. Prompts and confirmations both have preset themes, but you can customize them by using the custom option.

textColor
The color of the text. The default is set to white.

message
The message to be displayed in your alert.

duration
The duration in seconds to show the alert for. The default is 1.5 seconds.

speed
The speed to drop and raise the alert in milliseconds. The default is set to 500.

closeConfirm
Set this to true if you would like the user to have to close the alert rather than it disappearing by itself. The default is set to false.

upper
Set this to true if you would like your message in all uppercase letters. The default is set to false.

easing
jQuery UI easing option for the drop effect. The default is set to "easeOutBounce"

html
This is a boolean if the message argument should be interpreted as HTML. The default value is set to false.

overlay
Set this to true if you would like to have an overlay displayed with your alert. The default value is set to false. You can also pass in a value to the overlayColor argument to specify the color of the overlay. The default is set to black.

$("body").overhang({
  type: "confirm",
  message: "Do you want to continue?",
  closeConfirm: "true",
  overlay: true,
  overlayColor: "#1B1B1B"
});
Basic Alert Notification Example
// Some error notification
$("body").overhang({
  type: "error",
  message: "You could not be logged in at this time.",
  closeConfirm: "true"
});
Prompts
When using prompts, all you need to do is set the type parameter to "prompt".

Prompt Example
// Some prompt notification
$("body").overhang({
  type: "prompt",
  message: "What is your name"
});
Confirmations
When using confirmations, there are additional options that you can customize.

yesMessage
This is the text on the "true" button that would to display. The default is set to "Yes".

noMessage
This is the text on the "false" button that would to display. The default is set to "No".

yesColor
This is the color of the "true" button. The default is set to "#2ECC71".

noColor
This is the color of the "false" button. The default is set to "#E74C3C".

Confirmation Example
// Some confirmation
$("body").overhang({
  type: "confirm",
  yesMessage: "Yes please!",
  noMessage: "No thanks."
});
Retrieving Data
The prompt and confirm features both allow you to get data from the user. The responses are stored as data in the DOM of the target element that overhang.js has been applied to.

To retrieve the data, you simply pass in a callback function with one parameter:

$("body").overhang({
  type: "prompt",
  message: "What is your name",
  callback: function (value) {
    alert("You entered " + value);
  }
});
or you manually access the data from the DOM:

alert($("target-element").data("overhangPrompt")); // From a prompt
alert($("target-element").data("overhangConfirm")); // From a confirmation
If the user has not yet given a response, the default values will be set to null.

Callbacks
The option callback argument is a function that will run once the user has made an action on the overhang notification. The callback will run after any of these cases:

The submission of a prompt
The selection on a confirmation
The close button on a normal notification with a true closeConfirm
The raise of a normal notification
Note: For confirmations or prompts, the callback will not run when the close button is clicked and nothing is selected.

Example
$("body").overhang({
	type: "confirm",
	message: "Are you sure?",

  // This code will run once an option is clicked.
	callback: function (selection) {
		alert("You made your selection of " + selection);
	}
});