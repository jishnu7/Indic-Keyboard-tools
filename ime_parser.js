// Author: Jishnu Mohan <jishnu7@gmail.com>

var http = require('http'),
  async = require('async'),
  fs = require('fs'),
  file = process.argv[2];

if(!file) {
  return console.log("Error: Pass file name as parameter");
}

var sort = [
  'Q','W','E','R','T','Y','U','I','O','P','\\{','\\}','\\|',
  'q','w','e','r','t','y','u','i','o','p','\\[','\\]','\\\\',
  'A','S','D','F','G','H','J','K','L','\\:', '\"',null,null,
  'a','s','d','f','g','h','j','k','l',';', '\'',null,null,
  'Z','X','C','V','B','N','M','\\<','\\>','\\?',null,null,null,
  'z','x','c','v','b','n','m',',','.','/',null,null,null,
  '`','1','2','3','4','5','6','7','8','9','0','\\-','\\=',
  '~','\\!','\\@','\\#','\\$','\\%','\\^','\\&','\\*','\\(','\\)','_','\\+'
];
var toHex = function(char) {
  var hex = char.charCodeAt(0).toString(16).toUpperCase();
  if(char.length < 4) {
    hex = "0" + hex;
  }
  return "&#x" + hex + ";";
};

var charDetails = function(char, callback) {
  var query = JSON.stringify({
      method:  "chardetails.getdetails",
      params: [char],
      id: ""
    });

  var req = http.request({
    host: "silpa.org.in",
    //host: "localhost",
    path: "/JSONRPC",
    port: '80',
    //port: '5000',
    method: "POST",
    headers: {
      "Accept": "application/json, text/javascript, */*;",
      "Content-Type": "application/json; charset=UTF-8",
      "Content-Length": query.length + 2
    }
  }, function (res) {
    var data = "";
    res.addListener('data', function(chunk) {
      data += chunk;
    });
    res.addListener('end', function() {
      var val=" ";
      console.log(data);
      try {
        val = JSON.parse(data);
        val = val.result[char].Name;
      } catch (e) {
      }
      callback(val);
    });
  }).end(query);
};

jQuery = {
  ime: {
    register: function(data) {
      var out = [],
        extra = [],
        calls = [],
        count = 0;

      data.patterns.forEach(function(key) {
        if(key[1] === "") {
          return;
        }
        calls.push(function(callback) {
          var char = key[1],
            pos = key[0],
            index = sort.indexOf(pos);

          console.log('---------------------------');
          console.log(++count, pos, char);
          charDetails(char, function(name) {
            if(index !== -1) {
              out[sort.indexOf(pos)] = '\t\t\t<!--' + pos + ' ' + char + ' ' + name + '-->\n' +
                '\t\t\t<Key\n' +
                '\t\t\t\tlatin:keyLabel="' + toHex(char) +'"\n' +
                '\t\t\t\tlatin:keyLabelFlags="fontNormal|followKeyLargeLabelRatio" />\n';
            } else {
              extra.push(pos + " " + toHex(char) + " " + char + name + "\n");
            }
            callback();
          });
        });
      });

      async.series(calls, function(err, results) {
        var stream = fs.createWriteStream(file.split('.')[0] + '.xml');
        stream.once('open', function(fd) {
          out.forEach(function(line) {
            stream.write(line.replace(/\t/g, "    "));
          });
          stream.write("\n\n\n");
          extra.forEach(function(line) {
            stream.write(line.replace(/\t/g, "    "));
          });
          stream.end();
        });
      });
    }
  }
};

var a = require("./" + file);
