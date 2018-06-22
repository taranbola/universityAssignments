Cufon.replace('#slider .title, .button1', { fontFamily: 'Vegur', hover:true });

document.addEventListener("DOMContentLoaded", function() {

    $(window).load(function() {
        $('#slider')._TMS({
            banners: true, //allows right image
            preset: 'diagonalFade',
            pagination: true, //nav buttons
            duration: 400, //how quick left image moves
            slideshow: 8000, //how long goes back to start
            bannerShow: function(banner) {
                banner.css({
                    marginRight: -500
                }).stop().animate({
                    marginRight: 0
                }, 600) //makes it slide and not appear
            },
            bannerHide: function(banner) {
                banner.stop().animate({
                    marginRight: -500
                }, 600) //hides previous pages
            }
        })
    })

    jQuery.extend(jQuery.easing, {
        def: 'easeOutQuad',

        easeInQuad: function(x, t, b, c, d) { //used in presets
            return c * (t /= d) * t + b;
        },
    });

    (function($, undefined) {
        $.extend(_TMS, {
            presets: {
                diagonalFade: {
                    "reverseWay": false,
                    "duration": 400,
                    "interval": 40,
                    "blocksX": 1,
                    "blocksY": 12,
                    "easing": "easeInQuad",
                    "way": "gSnake",
                    "anim": "fade"
                },
            },
            ways: {
                gSnake: function() {
                    var opt = this,
                        ret = [],
                        h = opt.blocksY,
                        w = opt.blocksX,
                        j, i
                    for (i = 0; i < h; i++) //used in preset
                        for (j = 0; j < w; j++)
                            if (i * .5 == ~~(i / 2)) ret.push(opt.matrix[i][j])
                    else ret.push(opt.matrix[i][w - 1 - j])
                    return ret
                },
            },

            anims: {
                fade: function(el, last) { //used in preset
                    var opt = this
                    $(el).each(function() {
                        $(this).css({
                            opacity: 0
                        }).show().stop().animate({
                            opacity: 1
                        }, {
                            duration: +opt.duration,
                            easing: opt.easing,
                            complete: function() {
                                if (last) opt.afterShow()
                            }
                        })
                    })
                },
            }
        })
    })(jQuery)
});
