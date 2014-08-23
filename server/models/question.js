'use strict';

var mongoose = require('mongoose');

var QuestionModel = function() {
    var questionSchema = mongoose.Schema({
        text: String,
        imageUrl: String,
        yesCount: { type: Number, min: 0, default: 0 },
        noCount: { type: Number, min: 0, default: 0 }
    });

    return mongoose.model('Question', questionSchema);
};

module.exports = new QuestionModel();
