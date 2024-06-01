const { defineConfig } = require("@vue/cli-service");
const AutoImport = require("unplugin-auto-import/webpack");
const Components = require("unplugin-vue-components/webpack");
const { ElementPlusResolver } = require("unplugin-vue-components/resolvers");

module.exports = defineConfig({
  transpileDependencies: true,
  // chainWebpack: (config) => {
  //   config.resolve.alias.set("vue-i18n", "vue-i18n/dist/vue-i18n.cjs.js");
  // },
  // 对Webpack进行配置
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
    ],
  },
  devServer: {
    client: {
      overlay: {
        runtimeErrors: false,
      },
    },
    // 代理,解决跨域问题。但打包上线后就没有这个代理了，需要后端解决跨域问题
    proxy: {
      "/api": {
        //匹配所有以'/api'开头的请求路径
        target: process.env.VUE_APP_Proxy_Url, // 代理目标的基础路径
        pathRewrite: { "^/api": "" }, // 代理往后端服务器的请求去掉/api前缀
        ws: true, // WebSocket
        changeOrigin: true,
      },
    },
  },
});
