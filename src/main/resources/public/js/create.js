var $imageDisplay = $('#image-display');

function addImage(pImage, id) {
	var grid = $('<div/>', {
		class: 'uk-grid'
	});

	var container = $('<div/>', {
		class: 'uk-width-1-1'
	}).appendTo(grid);

	var figure = $('<figure/>', {
		class: 'uk-thumbnail uk-thumbnail-expand'
	}).appendTo(container);

	var image = $('<img/>', {
		src: '/images/' + pImage,
		alt: 'image-' + id
	}).appendTo(figure);

	var caption = $('<figcaption/>', {
		class: 'uk-thumbnail-caption',
		html: 'Click here to add a caption!'
	}).appendTo(figure);

	grid.appendTo($imageDisplay);
	console.log('done');
}

$(function() {
	var progressbar = $("#progressbar"),
		bar = progressbar.find('.uk-progress-bar'),
		settings = {
			action: '/api/images/upload', // upload url
			allow : '*.(jpg|jpeg|gif|png)', // allow only images,
			param : 'image',
			type : 'json',
			filelimit: false,

			loadstart: function() {
				bar.css("width", "0%").text("0%");
				progressbar.removeClass("uk-hidden");
			},

			progress: function(percent) {
				percent = Math.ceil(percent);
				bar.css("width", percent+"%").text(percent+"%");
			},

			complete: function(response) {
				console.log(JSON.stringify(response));
				if (!response.error) {
					addImage(response.result.file, response.result.id);
					console.log('add image');
				}
			},

			allcomplete: function (response) {
				bar.css("width", "100%").text("100%");

				setTimeout(function(){
					progressbar.addClass("uk-hidden");
				}, 2000);
			}
		};

	var select = UIkit.uploadSelect($("#upload-select"), settings),
		drop   = UIkit.uploadDrop($("#upload-drop"), settings);
});