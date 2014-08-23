'use strict';

var questionsLib = require('./questions'),
    db = require('./database');

module.exports = function(app) {
  return {
    onconfig: function(config, next) {
          var dbConfig = config.get('databaseConfig');
          db.config(dbConfig);

          next(null, config);
        }
  };
};
