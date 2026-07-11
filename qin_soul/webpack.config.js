const path = require("path");

module.exports = {
  entry: "./build/browser.js",
  devtool: 'inline-source-map',
  output: {
    path: path.resolve(__dirname, "public"),
    filename: "browser.js",
  },
};
