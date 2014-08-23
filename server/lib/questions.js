'use strict';

var Question = require('../models/question');

exports.getAllQuestions = function(callback) {
  Question.find({}).exec(callback);
};

exports.addStubQuestions = function() {
  // Remove existing questions.
  Question.find({}).remove(function(err, res) {
    // Add new stub questions.
    var q1 = new Question({
      text: 'יש לה קרציה בין הבהונות',
      imageUrl: 'http://upload.wikimedia.org/wikipedia/en/4/43/The_Ramen_Girl_poster.jpg',
      yes_count: 13,
      no_count: 4
    });
    var q2 = new Question({
      text: 'יש לה 2 קרציות בין הבהונות',
      imageUrl: 'http://upload.wikimedia.org/wikipedia/en/4/43/The_Ramen_Girl_poster.jpg',
      yes_count: 20,
      no_count: 1
    });

    q1.save();
    q2.save();
  });
};
