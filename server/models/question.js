'use strict';

var mongoose = require('mongoose');

var QuestionModel = function() {
    var questionSchema = mongoose.Schema({
        text: String,
        imageUrl: String,
        yes_count: { type: Number, min: 0, default: 0 },
        no_count: { type: Number, min: 0, default: 0 }
    });

    return mongoose.model('Question', questionSchema);
};

module.exports = new QuestionModel();
