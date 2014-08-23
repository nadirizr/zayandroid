'use strict';

var mongoose = require('mongoose');

exports.config = function(conf) {
  mongoose.connect('mongodb://' + conf.host + '/' + conf.database);
  var db = mongoose.connection;
  db.on('error', console.error.bind(console, 'Connection Error: '));
  db.once('open', function callback() {
    console.log('DB Connection Open.');
  });
};
