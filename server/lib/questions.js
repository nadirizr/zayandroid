'use strict';

var Question = require('../models/question');

exports.getAllQuestions = function(callback) {
  Question.find({}).exec(callback);
};

exports.getQuestion = function(id, callback) {
  Question.findOne({ _id: id }).exec(callback);
};

exports.createQuestion = function(data, callback) {
  var q = new Question(data);

  if (callback) {
    q.save(function(err) {
      return callback(err, q);
    });
  } else {
    q.save();
  }
};

exports.updateQuestion = function(id, answer, callback) {
  exports.getQuestion(id, function(err, question) {
    if (err) {
      return callback(err, question);
    }
    if (!question) {
      return callback('Question not found: ' + question, null);
    }

    if (answer == 'yes') {
      question.yes_count++;
    } else if (answer == 'no') {
      question.no_count++;
    } else {
      return callback('Invalid answer: ' + answer, null);
    }

    question.save(function(err) {
      return callback(err, question);
    });
  });
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
