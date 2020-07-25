const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');
const webpack = require('webpack');

const ENV = 'production';

module.exports = {
    mode: ENV,
    entry: './src/ui/index.js',
    output: {
        path: path.resolve(__dirname, 'target/classes/public/'),
        filename: 'bundle.js',
        libraryTarget: 'umd'
    },

    module: {
        rules: [
            {
                test: /\.js$/,
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
                NODE_ENV: JSON.stringify("production"),
                SERVER_API_URL: `''`
            }
        }),
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'src/ui/index.html'
        })
    ]
}
