const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');
const webpack = require('webpack');

const ENV = 'development';

module.exports = {
    mode: ENV,
    entry: './src/ui/index.js',
    devtool: 'eval-source-map',
    output: {
        path: path.resolve(__dirname),
        filename: 'bundle.js',
        libraryTarget: 'umd',
        publicPath: '/'
    },

    devServer: {
        contentBase: path.resolve(__dirname) + '/src/ui',
        port: 9000,
        host: 'localhost',
        open: true,
        historyApiFallback: true,
        proxy: [{
            context: [
                '/api',
            ],
            target: `http://localhost:8080`,
            secure: false,
        }],
    },

    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules|bower_components|build)/,
                use: {loader: 'babel-loader'},
            },
            {
                test: /\.(css)$/,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(png|jpg|jpeg|gif|ico|svg)$/,
                use: [
                    {
                        loader: 'file-loader',
                        options:
                            {
                                outputPath: 'images',
                                name: '[name]-[sha1:hash:7].[ext]'
                            }
                    }
                ]
            },
        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: JSON.stringify("development"),
                SERVER_API_URL: `''`
            }
        }),
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'src/ui/index.html'
        })
    ]
}
