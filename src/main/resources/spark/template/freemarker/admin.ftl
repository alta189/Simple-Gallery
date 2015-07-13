<!DOCTYPE html>
<html lang="en-gb" dir="ltr" class="uk-height-1-1">
<head>
	<meta charset="utf-8">
	<title>${gallery_name} | Admin Console</title>
	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
	<link rel="apple-touch-icon-precomposed" href="images/apple-touch-icon.png">
	<link rel="stylesheet" href="css/uikit.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/notify.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/sticky.almost-flat.min.css">
	<link rel="stylesheet" href="css/gallery.css">
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="js/uikit.min.js"></script>
	<script src="js/components/notify.min.js"></script>
	<script src="js/components/sticky.min.js"></script>
	<script src="js/messages.js"></script>
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/codemirror/5.4.0/codemirror.min.css">
	<script src="//cdnjs.cloudflare.com/ajax/libs/codemirror/5.4.0/codemirror.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/codemirror/5.4.0/mode/markdown/markdown.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/codemirror/5.4.0/addon/mode/overlay.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/codemirror/5.4.0/mode/xml/xml.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/codemirror/5.4.0/mode/gfm/gfm.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/marked/0.3.2/marked.min.js"></script>
	<script src="/js/components/htmleditor.min.js"></script>
	<link rel="stylesheet" href="/css/components/htmleditor.almost-flat.min.css">
	<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
	<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
</head>
<body>
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
							<span id="user-name"></span><br />
							<span id="user-email"></span>
						</div>
					</div>
				</div>
				<li class="uk-nav-divider"></li>
				<li><a href="/signin"><i class="uk-icon-sign-in"></i> &nbsp;Sign In</a></li>
				<li><a href="#"><i class="uk-icon-picture-o"></i> &nbsp;Create Album</a></li>
				<li class="uk-active"><a href="#"><i class="uk-icon-terminal"></i> &nbsp;Admin</a></li>
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

	<div class="uk-margin-large-top uk-margin-large-bottom">
		<div class="uk-container uk-container-center">
			<div class="uk-grid uk-margin-bottom" data-uk-grid-margin>
				<div class="uk-width-1-1">
					<h1 class="uk-heading-large" align="center">Simple Gallery</h1>
					<p class="uk-text-large" align="center">Admin Console</p>
				</div>
			</div>
		</div>
		<hr>
		<div class="uk-container uk-container-center">
			<div class="uk-grid uk-margin-top">
				<div class="uk-width-2-10">
					<!-- This is the vertical tabbed navigation containing the toggling elements -->
					<ul class="uk-tab uk-tab-left" data-uk-tab="{connect:'#settings-switcher'}">
						<li class="uk-active"><a href="#">Server Settings</a></li>
						<li><a href="#">Manage Users</a></li>
						<li><a href="#">Validation Rules</a></li>
						<li><a href="#">Verify Email Template</a></li>
						<li><a href="#">Reset Pass Template</a></li>
					</ul>
				</div>
				<div class="uk-width-8-10">
					<!-- This is the container of the content items -->
					<ul id="settings-switcher" class="uk-switcher">
						<li class="uk-active">
							<form class="uk-form uk-form-stacked" id="form-settings">
								<fieldset>
									<div class="uk-form-row">
										<label class="uk-form-label" for="gallery-name">Gallery Name</label>
										<div class="uk-form-controls">
											<input id="gallery-name" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="gallery-description">Gallery Description</label>
										<div class="uk-form-controls">
											<input id="gallery-description" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="gallery-host">Gallery Host</label>
										<div class="uk-form-controls">
											<input id="gallery-host" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="gallery-webadmin">Gallery Web Administrator</label>
										<div class="uk-form-controls">
											<input id="gallery-webadmin" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="sendgrid-user">SendGrid User</label>
										<div class="uk-form-controls">
											<input id="sendgrid-user" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="sendgrid-password">SendGrid Password</label>
										<div class="uk-form-controls">
											<input id="sendgrid-password" type="password" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="sendgrid-apikey">SendGrid API Key</label>
										<div class="uk-form-controls">
											<input id="sendgrid-apikey" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="email-verify">Email to Send Verification Emails From</label>
										<div class="uk-form-controls">
											<input id="email-verify" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="email-reset">Email to Send Password Reset Emails From</label>
										<div class="uk-form-controls">
											<input id="email-reset" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="email-contact-us">Contact Us Email</label>
										<div class="uk-form-controls">
											<input id="email-contact-us" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row uk-grid">
										<div class="uk-width-1-3"></div>
										<button type="submit" class="uk-width-1-3 uk-button uk-button-primary uk-button-large">Save</button>
										<div class="uk-width-1-3"></div>
									</div>
								</fieldset>
							</form>
						</li>
						<li>
							<div class="uk-grid">
								<table class="uk-table uk-table-striped">
									<thead>
										<tr class="uk-table-middle">
											<th class="uk-text-center">Name</th>
											<th class="uk-text-center">Email</th>
											<th class="uk-text-center">Verified Email</th>
											<th class="uk-text-center">Role</th>
											<th class="uk-text-center">Password</th>
											<th class="uk-text-center">Delete</th>
										</tr>
									</thead>
									<tbody id="users-list">
									</tbody>
								</table>
							</div>
						</li>
						<li>
							<div class="uk-grid">
								<table class="uk-table uk-table-striped">
									<thead>
										<tr class="uk-table-middle">
											<th class="uk-text-center">Validation Rule</th>
											<th class="uk-text-center">Validation Type</th>
											<th class="uk-text-center">Delete</th>
										</tr>
									</thead>
									<tbody id="validations-list">
									</tbody>
								</table>
							</div>
							<br />
							<hr>
							<div>
								<form class="uk-form uk-form-horizontal" id="form-validation-add">
									<div class="uk-form-row">
										<label class="uk-form-label" for="validation-rule">Validation Rule</label>
										<div class="uk-form-controls">
											<input id="validation-rule" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<label class="uk-form-label" for="validation-type">Validation Type</label>
										<div class="uk-form-controls">
											<select id="validation-type" style="width: 682px"></select>
										</div>
									</div>
									<div class="uk-form-row uk-grid">
										<div class="uk-width-1-3"></div>
										<button type="submit" class="uk-width-1-3 uk-button uk-button-primary uk-button-large">Add</button>
										<div class="uk-width-1-3"></div>
									</div>
								</form>
							</div>
						</li>
						<li>
							<form class="uk-form uk-form-stacked" id="form-verify">
								<fieldset>
									<div class="uk-form-row">
										<label class="uk-form-label" for="template-verify-subject">Subject:</label>
										<div class="uk-form-controls">
											<input id="template-verify-subject" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<textarea id="template-verify" data-uk-htmleditor="{markdown:true,mode:'split',maxsplitsize:600}">${editor_verify}</textarea>
									</div>
									<div class="uk-form-row uk-grid">
										<div class="uk-width-1-3"></div>
										<button type="submit" class="uk-width-1-3 uk-button uk-button-primary uk-button-large">Save</button>
										<div class="uk-width-1-3"></div>
									</div>
								</fieldset>
							</form>
							<h2 style="margin-bottom: 0">Variables</h2>
							<hr style="margin: 2px;">
							<dl class="uk-description-list-horizontal">
								<dt>{{gallery_name}}</dt><dd>Name of the Gallery</dd>
								<dt>{{gallery_description}}</dt><dd>Description of the Gallery</dd>
								<dt>{{gallery_host}}</dt><dd>Host of the Gallery <em>(EG: http://mygallery.com)</em></dd>
								<dt>{{gallery_webadmin}}</dt><dd>Web Administrator for the Gallery</dd>
								<dt>{{user_name}}</dt><dd>Name of the User receiving the email</dd>
								<dt>{{user_email}}</dt><dd>Email of the User receiving the email</dd>
								<dt>{{email_contact_us}}</dt><dd>Contact Us Email address</dd>
								<dt>{{now}}</dt><dd>Current Date and Time</dd>
								<dt>{{email_verification_link}}</dt><dd>Email confirmation link</dd>
							</dl>
						</li>
						<li>
							<form class="uk-form uk-form-stacked" id="form-reset">
								<fieldset>
									<div class="uk-form-row">
										<label class="uk-form-label" for="template-reset-subject">Subject:</label>
										<div class="uk-form-controls">
											<input id="template-reset-subject" type="text" class="uk-form-large uk-width-1-1">
										</div>
									</div>
									<div class="uk-form-row">
										<textarea id="template-reset" data-uk-htmleditor="{markdown:true,mode:'split',maxsplitsize:600}">${editor_reset}</textarea>
									</div>
									<div class="uk-form-row uk-grid">
										<div class="uk-width-1-3"></div>
										<button type="submit" class="uk-width-1-3 uk-button uk-button-primary uk-button-large">Save</button>
										<div class="uk-width-1-3"></div>
									</div>
								</fieldset>
							</form>
							<h2 style="margin-bottom: 0">Variables</h2>
							<hr style="margin: 2px;">
							<dl class="uk-description-list-horizontal">
								<dt>{{gallery_name}}</dt><dd>Name of the Gallery</dd>
								<dt>{{gallery_description}}</dt><dd>Description of the Gallery</dd>
								<dt>{{gallery_host}}</dt><dd>Host of the Gallery <em>(EG: http://mygallery.com)</em></dd>
								<dt>{{gallery_webadmin}}</dt><dd>Web Administrator for the Gallery</dd>
								<dt>{{user_name}}</dt><dd>Name of the User receiving the email</dd>
								<dt>{{user_email}}</dt><dd>Email of the User receiving the email</dd>
								<dt>{{email_contact_us}}</dt><dd>Contact Us Email address</dd>
								<dt>{{now}}</dt><dd>Current Date and Time</dd>
								<dt>{{password_reset_link}}</dt><dd>Password Reset link</dd>
							</dl>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script src="js/side-nav.js"></script>
	<script src="js/admin.js"></script>
</body>
</html>
