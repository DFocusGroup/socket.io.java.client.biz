module.exports = {
  title: "socket.io.java.client.biz",
  description: "DFocus wanted ssp solution - java-client",
  serviceWorker: true,
  base: "/socket.io.java.client.biz/",
  themeConfig: {
    home: true,
    logo: "/favicon.png",
    nav: [
      { text: "API", link: "/api/" },
      {
        text: "Github",
        link: "https://github.com/DFocusFE/socket.io.java.client.biz"
      }
    ],
    sidebar: {
      "/api/": [""]
    },
    serviceWorker: {
      updatePopup: true
    }
  }
};
