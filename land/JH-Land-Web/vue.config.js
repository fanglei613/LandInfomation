const path = require("path");

function resolve(dir) {
  return path.join(__dirname, dir);
}
module.exports = {
  /* 部署应用包的基本Url,默认会部署在域名的根路径上,如果应用部署在一个子路径上，则需要加上/子路径名称 */
  publicPath: process.env.NODE_ENV === "production" ? "/land" : "/",
  /* 生成生产环境包所在的目录 */
  outputDir: "../../land",
  /* 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录 */
  assetsDir: "static",
  /* 指定生成的 index.html 的输出路径 (相对于 outputDir)。也可以是一个绝对路径 */
  indexPath: "index.html",
  /* 指定生成文件名中包含hash default: true */
  filenameHashing: true,
  /* 是否启用多页应用 */
  //pages:
  /* 当lintOnSave为一个trusy值的时候eslint 会检测生产与开发环境的错误 */
  lintOnSave: process.env.NODE_ENV === "production",
  /* 生产环境的source map,设置为false的时候可以加快生产环境构建*/
  productionSourceMap: true,
  /* 部署在cdn上使用可增加安全性 */
  integrity: false,
  /* 给webpack添加更多配置 */
  configureWebpack: {
    resolve: {
      extensions: [".js", ".vue", ".json", ".css", ".less"],
      alias: {
        vue$: "vue/dist/vue.esm.js",
        "@": resolve("src"),
        assets: "@/assets",
        components: "@/components",
        views: "@/views",
        router: resolve("src/router"),
        public: resolve("public"),
        store: resolve("src/store"),
        pages: resolve("src/pages"),
        utils: resolve("src/utils")
      }
    }
  },
  /* 对webpack更细粒度的配置 */
  chainWebpack: config => {
    // 修复HMR
    config.resolve.symlinks(true);
  },
  // css相关配置
  css: {
    // 是否使用css分离插件 ExtractTextPlugin
    extract: false,
    // 开启 CSS source maps?
    sourceMap: false,
    // css预设器配置项
    loaderOptions: {
      //css: {}
    },
    // 启用 CSS modules for all css / pre-processor files.
    requireModuleExtension: true
  },
  /* 开发服务器 */
  devServer: {
    port: 8081,
    host: "0.0.0.0",
    https: false,
    open: true,
    hot: true,
    proxy: {
		"/api": {
			target: "http://localhost:8001",
			changeOrigin: true,
			ws: true,
			pathRewrite: {
				"^/api": "" // 设置过滤关键字api
				}
			}
		}
	}
};
