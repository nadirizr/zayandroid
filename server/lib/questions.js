'use strict';

var Question = require('../models/question');

exports.getAllQuestions = function(callback) {
  Question.find({}).exec(callback);
};

exports.getQuestionsSubset = function(start, limit, callback) {
  Question.find({}).sort({ _id: -1 }).skip(start).limit(limit).exec(callback);
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
      question.yesCount++;
    } else if (answer == 'no') {
      question.noCount++;
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
    for (var i = 2; i < 22; ++i) {
      var q = new Question({
        text: 'יש לה ' + i + ' קרציות בין הבהונות ',
        imageUrl: 'http://upload.wikimedia.org/wikipedia/en/4/43/The_Ramen_Girl_poster.jpg',
        yesCount: (i+1),
        noCount: (i+1)
      });
      q.save();
    }
  });
};
