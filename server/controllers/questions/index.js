'use strict';

var questionLib = require('../../lib/questions');

module.exports = function (router) {

    router.get('/', function (req, res) {
        
        questionLib.getAllQuestions(function(err, questions) {
            res.json(questions);
        });

    });

    router.post('/', function (req, res) {
        
        var data = {
          text: req.body.text,
          imageUrl: req.body.imageUrl
        };
        questionLib.createQuestion(data, function(err, question) {
            if (err) {
              console.log('Error saving new question: ' + data);
              res.json({ error: data });
              return;
            }
            
            res.json(question);
        });

    });

};
