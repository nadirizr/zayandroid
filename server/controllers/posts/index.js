'use strict';

var QuestionLibrary = require('../../lib/questions');

module.exports = function (router) {

    router.get('/', function (req, res) {
        
        QuestionLibrary.getAllQuestions(function(err, questions) {
            res.json(questions);
        });

    });

};
