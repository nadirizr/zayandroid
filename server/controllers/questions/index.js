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

    router.put('/:id', function (req, res) {
        
        questionLib.updateQuestion(req.params.id, req.body.answer,
                                   function(err, question) {
            if (err) {
              var error_string = 'Error saving new question (id=' +
                                 req.params.id +
                                 ', answer=' +
                                 req.body.answer + '): ' +
                                 err;
              console.log(error_string);
              res.json({ error: error_string });
              return;
            }
            
            res.json(question);
        });

    });

};