'use strict';


var PostsModel = require('../../models/posts');


module.exports = function (router) {

    var model = new PostsModel();
    var posts = model.posts();

    router.get('/', function (req, res) {
        
        res.format({
            json: function () {
                res.json(posts);
            }
        });
    });

};
