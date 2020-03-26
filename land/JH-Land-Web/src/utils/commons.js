export default {
    /**
     * 设置cookie值
     * @param key :
     * @param value:
     * @param expires :
     * @version <1> 2017-11-14 Hayden:Created.
     */
    setCookie: function (key, value, minuties) {
        let obj = this;
        if (key && value) {
            let expires = obj.getExpires(minuties);
            document.cookie = key + "=" + value + ";expires=" + expires;
        }
    },

    /**
     * 获取Cookie过期时间*
     * @version <1> 2017-12-24 Hayden:Created.
     */
    getExpires: function (minuties) {
        if (!minuties) {
            minuties = 30;
        }
        let exp = new Date();
        exp.setTime(exp.getTime() + 60 * 1000 * minuties); //过期时间 30分钟
        return exp.toGMTString();
    }
}