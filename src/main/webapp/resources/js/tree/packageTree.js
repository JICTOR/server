function Tree(selector, pathList_) {
    this.$el = $(selector);
    var html_ = [];
    var tree_ = {};
    var self = this;

    this.render = function (object) {
        if (object) {
            for (var folder in object) {
                if (isNumber(object[folder])) {
                    html_.push('<li><a href="#" data-type="file" data-index=', object[folder], '>', folder, '</a></li>');
                } else {
                    html_.push('<li><a href="#">', folder, '</a>');
                    html_.push('<ul>');
                    self.render(object[folder]);
                    html_.push('</ul>');
                }
            }
        }
    };

    this.buildFromPathList = function (paths) {
        for (var i = 0, path; path = paths[i]; ++i) {
            var pathParts = path.split('.');
            var subObj = tree_;
            for (var j = 0, folderName; folderName = pathParts[j]; ++j) {
                if (!subObj[folderName]) {
                    subObj[folderName] = j < pathParts.length - 1 ? {} : i;
                }
                subObj = subObj[folderName];
            }
        }
        $("#classNum").text(i);
        return tree_;
    }

    this.init = function (e) {
        self.render(self.buildFromPathList(pathList_));
        self.$el.html(html_.join('')).tree({
            expanded: 'li:first'
        });
    }
};

function isNumber(s) {
    s += '';
    s = s.replace(/^\s*|\s*$/g, '');
    if (s == '' || isNaN(s)) return false;
    return true;
};

function packageJsonDrawTree(drawPlaceId, json) {
    var tree = new Tree(drawPlaceId, json);
    tree.init();

    tree.$el.click(function (e) {
        if (e.target.nodeName == 'A' && e.target.dataset['type'] == 'file') {
            var fileId = e.target.dataset['index'];
            //var codeViewer = window.open("code_view/" + fileId, fileId);
        }
    });
};
