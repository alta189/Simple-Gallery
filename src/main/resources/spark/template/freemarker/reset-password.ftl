<!DOCTYPE html>
<html lang="en-gb" dir="ltr" class="uk-height-1-1">
<head>
	<meta charset="utf-8">
	<title>${gallery_name} | Reset Password</title>
	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
	<link rel="apple-touch-icon-precomposed" href="images/apple-touch-icon.png">
	<link rel="stylesheet" href="css/uikit.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/notify.min.css">
	<link rel="stylesheet" href="css/sticky.css">
	<link rel="stylesheet" href="css/gallery.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<script src="js/uikit.min.js"></script>
	<script src="js/components/notify.min.js"></script>
</head>
<body class="uk-height-1-1">
	<div id="sticky-anchor"></div>
	<div id="sticky"><a class="uk-icon-button uk-icon-navicon" data-uk-offcanvas="{target: '#side-nav'}"></a></div>
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
							<span id="user-name"></span><br />
							<span id="user-email"></span>
						</div>
					</div>
				</div>
				<li class="uk-nav-divider"></li>
				<li class="uk-active"><a href="#"><i class="uk-icon-sign-in"></i> &nbsp;Sign In</a></li>
				<li><a href="#"><i class="uk-icon-picture-o"></i> &nbsp;Create Album</a></li>
				<li><a href="#"><i class="uk-icon-terminal"></i> &nbsp;Admin</a></li>
				<li class="uk-nav-divider"></li>
				<li><a href="#"><i class="uk-icon-support"></i> &nbsp;Contact Us</a></li>
				<div class="pinned-bottom">
					<div class="uk-panel" style="margin: 10px">
						<h3 class="uk-panel-title" style="color: #FFFFFF; margin-bottom: 5px">About Simple Gallery</h3>
						<hr style="color: #FFFFFF; margin-top: 0px; margin-bottom: 5px" size="10">
						<div class="wordwrap" style="color: #FFFFFF">
							&copy; 2015 Stephen Williams <br />
							<a href="https://raw.githubusercontent.com/alta189/Simple-Gallery/master/LICENSE">Licensed under New BSD License</a><br />
							<a href="https://github.com/alta189/Simple-Gallery">Source Code</a> <br />
							<a href="https://github.com/alta189/Simple-Gallery/issues">Report an Issue</a>
						</div>
					</div>
				</div>
			</ul>
		</div>
	</div>

	<div class="uk-vertical-align uk-text-center uk-height-1-1">
		<div class="uk-vertical-align-middle" style="width: 250px;">
			<form class="uk-panel uk-panel-box uk-form" id="reset-form">
				<div class="uk-form-row">
					<h4>Reset Password</h4>
				</div>
				<div class="uk-form-row">
					<input type="hidden" id="key" value="${reset_key}">
					<input class="uk-width-1-1 uk-form-large" type="text" id="email" placeholder="Email" value="${reset_email}" disabled>
				</div>
				<div class="uk-form-row">
					<input class="uk-width-1-1 uk-form-large" type="password" id="password" placeholder="New Password">
				</div>
				<div class="uk-form-row">
					<input class="uk-width-1-1 uk-form-large" type="password" id="confirm" placeholder="Confirm">
				</div>
				<div class="uk-form-row">
					<button type="submit" class="uk-width-1-1 uk-button uk-button-primary uk-button-large">Reset</button>
				</div>
			</form>
		</div>
	</div>
	<script src="js/side-nav.js"></script>
	<script src="js/reset.js"></script>
</body>
</html>
