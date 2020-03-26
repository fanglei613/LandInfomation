export default {
  //是否为空
  isEmpty: function(str) {
    if (str.trim().length < 1) {
      return true;
    }
    return false;
  },
  //手机号码格式是否正确
  isPhone: function(str) {
    var reg1 = /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;

    if (reg1.test(str.trim())) {
      return true;
    }
    return false;
  },
  //输入长度是否在正确范围
  isScopeLength: function(str, start, end) {
    var len = str.length;
    if (start <= len && len <= end) {
      return true;
    }
    return false;
  },
  //验证重复密码
  isPasswordTo: function(str, strTo) {
    if (str.trim() == strTo.trim()) {
      return true;
    }
    return false;
  },

  //是否为英文字母
  isLetter: function(str) {
    var reg = /^[A-Za-z ]+$/;
    return reg.test(str);
  },

  isEmail: function(str) {
    var reg = /^([A-Z0-9a-z\._%+-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    return reg.test(str.trim());
  },
  //非负浮点数
  isPositiveFloat: function(str) {
    //var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
    return reg.test(str.trim());
  },
  //qq
  isQQ: function(str) {
    var reg = /^[1-9][0-9]{4,14}$/;
    return reg.test(str.trim());
  },
  //正整数
  isPositiveInteger: function(str) {
    var reg = /^\+?[1-9][0-9]*$/;
    return reg.test(str);
  },
  //正负整数
  isPlusMinusInteger: function(str) {
    //var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    var reg = /^-?\d+$/;
    return reg.test(str.trim());
  },
  //正负浮点数
  isPlusMinusDouble: function(str) {
    //var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    var reg = /^(-?\d+)(\.\d+)?$/;
    return reg.test(str.trim());
  },
  //包含字母、数字和下划线
  isCharDigitalUnderline: function(str) {
    var reg = /^\w+$/;
    return reg.test(str);
  },
  //增加了 判断编码只能是数字，下划线，字母 或者可以包含中划线 /^[a-zA-Z0-9_-]+$/
  isCharDigitalUnderlineAndMiddleLine: function(str) {
    var reg = /^[a-zA-Z0-9_-]+$/;
    return reg.test(str);
  },
  //增加了 判断编码只能是数字，下划线，字母 或者可以包含.
  isCharDigitalUnderlineAndDot: function(str) {
    var reg = /^[a-zA-Z0-9_\.]+$/;
    return reg.test(str);
  },
  //只能输入中文
  isAllChinese: function(str) {
    var reg = /^[\u0391-\uFFE5]+$/;
    return reg.test(str);
  },
  /*
   * 检验手机号码输入
   * @param obj 被验证的对象
   * @param showTargetObj 信息显示所在的容器对象
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  checkPhoneInput: function(obj, showTargetObj) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (currentObj.isEmpty(obj.val())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml("手机号码不能为空"));
      return true;
    } else if (!currentObj.isPhone(obj.val())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml("手机号码格式不正确"));
      return true;
    }
    return false;
  },
  /*
   * 邮箱格式验证，允许邮箱为空
   * @param obj 被验证的对象
   * @param showTargetObj 信息显示所在的容器对象
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  checkEmailInput: function(obj, showTargetObj) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (currentObj.isEmpty(obj.val())) {
      return false;
    } else if (!currentObj.isEmail(obj.val())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml("邮箱格式不正确"));
      return true;
    }
    return false;
  },

  /*
   * 检查输入是否为英文
   * @param obj 被验证的对象
   * @param showTargetObj 信息显示所在的容器对象
   * @returns {boolean}
   * @version <1> 2018-04-04 cxw: created.
   */
  checkIsLetter: function(obj, showTargetObj) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (currentObj.isLetter(obj.val())) {
      return false;
    } else if (!currentObj.isLetter(obj.val())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml("编码为字母、数字、下划线或中划线组合"));
      return true;
    }
    return false;
  },
  /*
   * 检查输入长度
   * @param start 长度的起始值
   * @param end 长度的结束值
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  checkInputLength: function(start, end, obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (!currentObj.isScopeLength(obj.val(), start, end)) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },

  /*
   * 检查数据集值输入长度
   * @param start 长度的起始值
   * @param end 长度的结束值
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-06-20 cxw: created.
   */
  checkDsInputLength: function(
    start,
    end,
    obj,
    showTargetObj,
    showInfo,
    showInfoTwo
  ) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);
    var val = obj.val();
    if (val % 1 == 0) {
      if (!currentObj.isScopeLength(val, 1, 4)) {
        obj.addClass("formInputHighlight");
        showTargetObj.append(getHtml(showInfoTwo));
        return true;
      }
    }
    if (!currentObj.isScopeLength(val, start, end)) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },

  /*
   * 检查输入的是否为数字
   * @param start 长度的起始值
   * @param end 长度的结束值
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  checkInputIsNumber: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (!currentObj.isPositiveFloat(obj.val().trim())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },
  /*
   * 检查输入是否为qq
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  checkInputIsQQ: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);
    if (currentObj.isEmpty(obj.val())) {
      //qq号可以为空
      return false;
    } else if (!currentObj.isQQ(obj.val().trim())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },
  /*
   * 检查输入是否为空
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  checkInputIsEmpty: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    var v = obj.val().trim();
    if (currentObj.isEmpty(v)) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },

  /*
   * 检查是否为正整数
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-07 cxj: created.
   */
  checkInputIsPositiveInteger: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    var v = obj.val().trim();
    if (v.length == 0) {
      return false;
    }
    if (!currentObj.isPositiveInteger(v)) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },
  /*
   * 检查输入是否包含字母、数字和下划线 或者可以包含中划线
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-4-8 lxy: created.
   */
  checkInputIsCharDigitalUnderlineMiddleLine: function(
    obj,
    showTargetObj,
    showInfo
  ) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (!currentObj.isCharDigitalUnderlineAndMiddleLine(obj.val().trim())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },
  /*
   * 显示提示信息
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  showPromptInfo: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    obj.addClass("formInputHighlight");
    showTargetObj.append(getHtml(showInfo));

    return true;
  },
  /*
   * 校验是否包含中文字符
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-07 djh: created.
   */
  checkChinese: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    var str = obj.val();
    for (var i = 0; i < str.length; i++) {
      if (str.charCodeAt(i) > 255) {
        obj.addClass("formInputHighlight");
        showTargetObj.append(getHtml(showInfo));
        return true;
      }
    }
    return false;
  },
  /*
   * 校验端口
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-08 djh: created.
   */
  checkPort: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    var port = $.trim(obj.val());
    if (port == "") {
      return false;
    }
    var num = parseInt(port);
    var exp = new RegExp("^\\d+$");
    if (port.match(exp) == null || port.length > 5 || num > 65535) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }

    return false;
  },
  /*
   * 检查输入长度和是否为空
   * @param start 长度的起始值
   * @param end 长度的结束值
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @param showNullInfo 为空的提示信息
   * @returns {boolean}
   * @version <1> 2018-02-08 djh: created.
   */
  checkInputLengthAndIsNull: function(
    start,
    end,
    obj,
    showTargetObj,
    showInfo,
    showNullInfo
  ) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if ($.trim(obj.val()) == "") {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showNullInfo));
      return true;
    }

    if (!currentObj.isScopeLength(obj.val(), start, end)) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },
  /*
   * 检查输入是否包含字母、数字和下划线 或者可以包含.
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-4-8 lxy: created.
   */
  checkInputIsCharDigitalUnderlineaAndDot: function(
    obj,
    showTargetObj,
    showInfo
  ) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (!currentObj.isCharDigitalUnderlineAndDot(obj.val().trim())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },
  /*
   * 添加错误提示信息
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-10 djh: created.
   */
  showErroInfo: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);
    showTargetObj.append(getHtml(showInfo));
  },
  /*
   * 检查输入是否包含字母、数字和下划线
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-09 cxj: created.
   */
  checkInputIsCharDigitalUnderline: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (!currentObj.isCharDigitalUnderline(obj.val().trim())) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }
    return false;
  },
  /*
   * 检查输入的是否为正负整数 或者正负浮点数
   * @param start 长度的起始值
   * @param end 长度的结束值
   * @param obj 被验证的输入对象
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-02-06 cxj: created.
   */
  checkInputIsPlusMinusNumber: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);

    if (
      !currentObj.isPlusMinusInteger(obj.val().trim()) &&
      !currentObj.isPlusMinusDouble(obj.val().trim())
    ) {
      obj.addClass("formInputHighlight");
      showTargetObj.append(getHtml(showInfo));
      return true;
    }

    return false;
  },
  //清除input框错误信息样式
  clearInputClass: function(obj) {
    obj
      .parents("body")
      .find(".formInputHighlight")
      .removeClass("formInputHighlight");
  },

  /*
   * 错误信息加载
   * @param showTargetObj 信息显示所在的容器对象
   * @param showInfo 提示信息
   * @returns {boolean}
   * @version <1> 2018-06-26 cxw: created.
   */
  showErrorInfo: function(obj, showTargetObj, showInfo) {
    var currentObj = this;
    clearErrorInfo(obj, showTargetObj);
    showTargetObj.append(getHtml(showInfo));
    return true;
  }
};

//清除错误信息
var clearErrorInfo = function(obj, showTargetObj) {
  if (showTargetObj.find(".formErrorInfo").length != 0) {
    obj
      .parents("body")
      .find(".formInputHighlight")
      .removeClass("formInputHighlight");
    showTargetObj.find(".formErrorInfo").remove();
  }
};

//清除input框错误信息样式
var clearInputClass = function(obj) {
  obj
    .parents("body")
    .find(".formInputHighlight")
    .removeClass("formInputHighlight");
};

//显示提示内容
var getHtml = function(msg) {
  return '<div class="formErrorInfo"><span>' + msg + "</span></div>";
};
