'use strict';

module.exports = function PostsModel() {
  return {
    posts: function() {
      return [
        {
          text: 'יש לה קרציה בין הבהונות',
          imageUrl: 'http://upload.wikimedia.org/wikipedia/en/4/43/The_Ramen_Girl_poster.jpg',
          yes: 13,
          no: 4,
        }
      ];
    }
  }
};
