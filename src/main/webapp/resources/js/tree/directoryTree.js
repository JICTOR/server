function Tree(selector) {
	this.$el = $(selector);
	this.fileList = [];
	var html_ = [];
	var tree_ = {};
	var pathList_ = [];
	var self = this;

	this.render = function(object) {
		if (object) {
			for ( var folder in object) {
				if (!object[folder]) {
					html_.push('<li><a href="#" data-type="file">', folder,
					'</a></li>');
				} else {
					html_.push('<li><a href="#">', folder, '</a>');
					html_.push('<ul>');
					self.render(object[folder]);
					html_.push('</ul>');
				}
			}
		}
	};

	this.buildFromPathList = function(paths) {
		for (var i = 0, path; path = paths[i]; ++i) {
			var pathParts = path.split('/');
			var subObj = tree_;
			for (var j = 0, folderName; folderName = pathParts[j]; ++j) {
				if (!subObj[folderName]) {
					subObj[folderName] = j < pathParts.length - 1 ? {} : null;
				}
				subObj = subObj[folderName];
			}
		}
		return tree_;
	}

	this.init = function(e) {
		html_ = [];
		tree_ = {};
		pathList_ = [];
		self.fileList = e.target.files;
		var filePath;
		for (var i = 0, file; file = self.fileList[i]; ++i) {
			filePath = file.webkitRelativePath;
			if (filePath.endsWith(".java")) {
				pathList_.push(filePath);
			}
		}
		self.render(self.buildFromPathList(pathList_));
		self.$el.html(html_.join('')).tree({
			expanded : 'li:first'
		});
		var fileNodes = self.$el.get(0).querySelectorAll("[data-type='file']");
		for (var i = 0, fileNode; fileNode = fileNodes[i]; ++i) {
			fileNode.dataset['index'] = i;
		}
	}
};