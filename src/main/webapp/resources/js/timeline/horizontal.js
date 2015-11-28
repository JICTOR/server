function horizontal(frameId) {
    var $frame = $(frameId);
    var $wrap = $frame.parent();

    // Call Sly on frame
    $frame.sly({
        horizontal: 1,
        itemNav: 'basic',
        smart: 1,
        activateOn: 'click',
        mouseDragging: 1,
        touchDragging: 1,
        releaseSwing: 1,
        startAt: 0,
        scrollBar: $wrap.find('.scrollbar'),
        scrollBy: 1,
        speed: 300,
        elasticBounds: 1,
        easing: 'easeOutExpo',
        dragHandle: 1,
        dynamicHandle: 1,
        clickBar: 1,

        // Cycling
        cycleBy: 'items',
        cycleInterval: 500,
        pauseOnHover: 1,

        // Buttons
        prev: $wrap.find('.prev'),
        next: $wrap.find('.next')
    });

    // Pause button
    $wrap.find('.pause').on('click', function () {
        $frame.sly('pause');
    });

    // Resume button
    $wrap.find('.resume').on('click', function () {
        $frame.sly('resume');
    });

    // Toggle button
    $wrap.find('.toggle').on('click', function () {
        $frame.sly('toggle');
    });
};