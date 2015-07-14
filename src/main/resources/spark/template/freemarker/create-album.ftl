<!DOCTYPE html>
<html lang="en-gb" dir="ltr" class="uk-height-1-1">
<head>
	<meta charset="utf-8">
	<title>${gallery_name} | Sign In</title>
	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
	<link rel="apple-touch-icon-precomposed" href="images/apple-touch-icon.png">
	<link rel="stylesheet" href="css/uikit.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/notify.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/sticky.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/form-file.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/placeholder.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/progress.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/upload.almost-flat.min.css">
	<link rel="stylesheet" href="css/gallery.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="js/uikit.min.js"></script>
	<script src="js/components/notify.min.js"></script>
	<script src="js/components/sticky.min.js"></script>
	<script src="js/components/upload.min.js"></script>
	<script src="js/messages.js"></script>
</head>
<body class="uk-height-1-1">
	<div class="uk-container" style="margin: 0; padding: 10px 0 0 10px">
		<div data-uk-sticky="{top: 10}"><a class="uk-icon-button uk-icon-navicon" data-uk-offcanvas="{target: '#side-nav'}"></a></div>
	</div>
	<div class="uk-container-center uk-margin-top uk-margin-large-bottom">
		<!-- This is the off-canvas sidebar -->
		<div id="side-nav" class="uk-offcanvas">
			<div class="uk-offcanvas-bar">
				<ul class="uk-nav uk-nav-offcanvas uk-nav-parent-icon" data-uk-nav>
					<li class="uk-nav-divider"></li>
					<li><a href="/"><i class="uk-icon-home"></i> &nbsp;Home</a></li>
					<div id="user-info-panel" class="hidden">
						<li class="uk-nav-divider"></li>
						<div class="uk-panel" style="margin: 10px">
							<h3 class="uk-panel-title" style="color: #FFFFFF; margin-bottom: 5px">Account Info</h3>
							<hr style="color: #FFFFFF; margin-top: 0px; margin-bottom: 5px" size="10">
							<div class="wordwrap" style="color: #FFFFFF">
								<span id="user-name"></span><br/>
								<span id="user-email"></span>
							</div>
						</div>
					</div>
					<li class="uk-nav-divider"></li>
					<li class="uk-active"><a href="#"><i class="uk-icon-sign-in"></i> &nbsp;Sign In</a></li>
					<li><a href="#"><i class="uk-icon-picture-o"></i> &nbsp;Create Album</a></li>
					<li><a href="/admin"><i class="uk-icon-terminal"></i> &nbsp;Admin</a></li>
					<li class="uk-nav-divider"></li>
					<li><a href="#"><i class="uk-icon-support"></i> &nbsp;Contact Us</a></li>
					<div class="pinned-bottom">
						<div class="uk-panel" style="margin: 10px">
							<h3 class="uk-panel-title" style="color: #FFFFFF; margin-bottom: 5px">About Simple Gallery</h3>
							<hr style="color: #FFFFFF; margin-top: 0px; margin-bottom: 5px" size="10">
							<div class="wordwrap" style="color: #FFFFFF">
								&copy; 2015 Stephen Williams <br/>
								<a href="https://raw.githubusercontent.com/alta189/Simple-Gallery/master/LICENSE">Licensed under New BSD License</a><br/>
								<a href="https://github.com/alta189/Simple-Gallery">Source Code</a> <br/>
								<a href="https://github.com/alta189/Simple-Gallery/issues">Report an Issue</a>
							</div>
						</div>
					</div>
				</ul>
			</div>
		</div>

		<div class="uk-margin-large-top uk-margin-large-bottom">
			<div class="uk-container uk-container-center">
				<div class="uk-grid uk-margin-bottom" data-uk-grid-margin>
					<div class="uk-width-1-1">
						<h1 class="uk-heading-large" align="center">${gallery_name}</h1>

						<p class="uk-text-large" align="center">Create Album</p>
					</div>
				</div>
			</div>
			<hr>
			<div class="uk-container uk-container-center">
				<div class="uk-grid">
					<div class="uk-width-1-4 uk-vertical-align-middle">
						<form class="uk-form uk-form-stacked">
							<fieldset>
								<div class="uk-form-row uk-width-1-1">
									<label class="uk-form-label uk-text-center" for="album-name">Album Name</label>

									<div class="uk-form-controls">
										<input id="gallery-name" type="text" class="uk-form-large uk-width-1-1">
									</div>
								</div>
								<div class="uk-form-row uk-width-1-1">
									<label class="uk-form-label uk-text-center" for="album-name">Album Description</label>

									<div class="uk-form-controls">
										<textarea id="gallery-name" class="uk-form-large uk-width-1-1" style="height: 120px"></textarea>
									</div>
								</div>
								<div class="uk-form-row">
									<div id="upload-drop" class="uk-placeholder uk-text-center uk-width-1-1">
										<i class="uk-icon-cloud-upload uk-icon-medium uk-text-muted uk-margin-small-right"></i> Add images by dropping them here or <a class="uk-form-file">selecting one<input id="upload-select" type="file"></a>.
									</div>
									<div id="progressbar" class="uk-progress uk-width-1-1 uk-hidden">
										<div class="uk-progress-bar" style="width: 100%;">100%</div>
									</div>
								</div>
								<div class="uk-form-row">
									<button type="submit" class="uk-button uk-button-primary uk-button-large uk-width-1-1">Save</button>
								</div>
							</fieldset>
						</form>
					</div>
					<div class="uk-width-3-4" id="image-display">
						<#--<div class="uk-grid">-->
							<#--<div class="uk-width-1-1">-->
								<#--<figure class="uk-thumbnail uk-thumbnail-expand">-->
									<#--<img src="img/placeholder_600x400.svg" alt="">-->
									<#--<figcaption onclick="alert('clicked 1')" class="uk-thumbnail-caption">Description</figcaption>-->
								<#--</figure>-->
							<#--</div>-->
						<#--</div>-->
						<#--<div class="uk-grid">-->
							<#--<div class="uk-width-1-1">-->
								<#--<figure class="uk-thumbnail uk-thumbnail-expand">-->
									<#--<img src="img/placeholder_600x400.svg" alt="">-->
									<#--<figcaption onclick="alert('clicked 2')" class="uk-thumbnail-caption">Description</figcaption>-->
								<#--</figure>-->
							<#--</div>-->
						<#--</div>-->
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="js/side-nav.js"></script>
	<script src="js/create.js"></script>
</body>
</html>
