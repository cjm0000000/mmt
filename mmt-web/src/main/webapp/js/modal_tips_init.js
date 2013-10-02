$(function() {
	$.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner = '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">'
			+ '<div class="progress progress-striped active">'
			+ '<div class="progress-bar" style="width: 100%;"></div>'
			+ '</div>' + '</div>';

	$.fn.modalmanager.defaults.resize = true;
	
	$('[data-source]').each(function() {
		var $this = $(this), $source = $($this.data('source'));

		var text = [];
		$source.each(function() {
			var $s = $(this);
			if ($s.attr('type') == 'text/javascript') {
				text.push($s.html().replace(/(\n)*/, ''));
			} else {
				text.push($s.clone().wrap('<div>').parent().html());
			}
		});
		$this.text(text.join('\n\n').replace(/\t/g, '    '));
	});
});