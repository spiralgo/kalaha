const Dotenv = require('dotenv-webpack');
function path(env){
    console.log(env);
    let path = __dirname + "/.env";
    if(env.production === undefined){
        path+= ".development";

    }else{
        path+= ".production";
    }
     return path;
}
module.exports = (env) =>( {
    plugins: [

        new Dotenv({
            path: path(env)
        }),

    ],
    devtool: 'source-map',
    output: {
        filename: 'react-app.js'
    },
    module: {
        rules: [{
            test: /\.(js|jsx)$/,
            exclude: /node_modules/,
            loader: "babel-loader",
            options: {
                presets: ['@babel/preset-env', '@babel/preset-react']
            }
        },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx']
    }
});