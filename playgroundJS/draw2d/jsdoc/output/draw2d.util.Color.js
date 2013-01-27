Ext.data.JsonP.draw2d_util_Color({"extends":null,"component":false,"meta":{},"linenr":1,"inheritdoc":null,"inheritable":null,"mixedInto":[],"aliases":{},"singleton":false,"mixins":[],"parentMixins":[],"statics":{"cfg":[],"property":[],"css_var":[],"method":[],"event":[],"css_mixin":[]},"files":[{"href":"Color.html#draw2d-util-Color","filename":"Color.js"}],"superclasses":[],"members":{"cfg":[],"property":[],"css_var":[],"method":[{"meta":{},"owner":"draw2d.util.Color","name":"constructor","id":"method-constructor","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"darker","id":"method-darker","tagname":"method"},{"meta":{"since":{"text":"","version":"2.1.0"}},"owner":"draw2d.util.Color","name":"fadeTo","id":"method-fadeTo","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"getBlue","id":"method-getBlue","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"getGreen","id":"method-getGreen","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"getHTMLStyle","id":"method-getHTMLStyle","tagname":"method"},{"meta":{"private":true,"deprecated":{"text":""}},"owner":"draw2d.util.Color","name":"getHashStyle","id":"method-getHashStyle","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"getIdealTextColor","id":"method-getIdealTextColor","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"getRed","id":"method-getRed","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"hash","id":"method-hash","tagname":"method"},{"meta":{"private":true},"owner":"draw2d.util.Color","name":"hex","id":"method-hex","tagname":"method"},{"meta":{"private":true},"owner":"draw2d.util.Color","name":"hex2rgb","id":"method-hex2rgb","tagname":"method"},{"meta":{"private":true},"owner":"draw2d.util.Color","name":"int2hex","id":"method-int2hex","tagname":"method"},{"meta":{},"owner":"draw2d.util.Color","name":"lighter","id":"method-lighter","tagname":"method"}],"event":[],"css_mixin":[]},"alternateClassNames":[],"override":null,"private":null,"html":"<div><pre class=\"hierarchy\"><h4>Files</h4><div class='dependency'><a href='source/Color.html#draw2d-util-Color' target='_blank'>Color.js</a></div></pre><div class='doc-contents'><p>Util class to handle colors in the draw2d enviroment.</p>\n\n<pre><code> // Create a new Color with RGB values\n var color = new draw2d.util.Color(127,0,0);\n\n // of from a hex string\n var color2 = new draw2d.util.Color(\"#f00000\");\n\n // Create a little bit darker color \n var darkerColor = color.darker(0.2); // 20% darker\n\n // create a optimal text color if 'color' the background color\n // (best in meaning of contrast and readability)\n var fontColor = color.getIdealTextColor();\n</code></pre>\n</div><div class='members'><div class='members-section'><div class='definedBy'>Defined By</div><h3 class='members-title icon-method'>Methods</h3><div class='subsection'><div id='method-constructor' class='member first-child not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-constructor' target='_blank' class='view-source'>view source</a></div><strong class='new-keyword'>new</strong><a href='#!/api/draw2d.util.Color-method-constructor' class='name expandable'>draw2d.util.Color</a>( <span class='pre'>red, green, blue</span> ) : <a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></div><div class='description'><div class='short'>Create a new Color object ...</div><div class='long'><p>Create a new Color object</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>red</span> : Number<div class='sub-desc'>\n</div></li><li><span class='pre'>green</span> : Number<div class='sub-desc'>\n</div></li><li><span class='pre'>blue</span> : Number<div class='sub-desc'>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-darker' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-darker' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-darker' class='name expandable'>darker</a>( <span class='pre'>fraction</span> ) : <a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></div><div class='description'><div class='short'>Returns a darker color of the given one. ...</div><div class='long'><p>Returns a darker color of the given one. The original color is unchanged.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>fraction</span> : Number<div class='sub-desc'><p>Darkness fraction between [0..1].</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></span><div class='sub-desc'><p>Darker color.</p>\n</div></li></ul></div></div></div><div id='method-fadeTo' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-fadeTo' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-fadeTo' class='name expandable'>fadeTo</a>( <span class='pre'>color, pc</span> ) : <a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></div><div class='description'><div class='short'>Return a new color wich is faded to the given color. ...</div><div class='long'><p>Return a new color wich is faded to the given color.</p>\n              <p class=\"pre\">Since:<strong> 2.1.0</strong></p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>color</span> : <a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a><div class='sub-desc'>\n</div></li><li><span class='pre'>pc</span> : Number<div class='sub-desc'><p>the fade percentage in [0..1]</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-getBlue' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-getBlue' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-getBlue' class='name expandable'>getBlue</a>( <span class='pre'></span> ) : Number</div><div class='description'><div class='short'>The blue part of the color ...</div><div class='long'><p>The blue part of the color</p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'>Number</span><div class='sub-desc'><p>the [blue] part of the color.</p>\n</div></li></ul></div></div></div><div id='method-getGreen' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-getGreen' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-getGreen' class='name expandable'>getGreen</a>( <span class='pre'></span> ) : Number</div><div class='description'><div class='short'>The green part of the color. ...</div><div class='long'><p>The green part of the color.</p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'>Number</span><div class='sub-desc'><p>the [green] part of the color.</p>\n</div></li></ul></div></div></div><div id='method-getHTMLStyle' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-getHTMLStyle' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-getHTMLStyle' class='name expandable'>getHTMLStyle</a>( <span class='pre'></span> ) : String</div><div class='description'><div class='short'>Convert the color object into a HTML CSS representation ...</div><div class='long'><p>Convert the color object into a HTML CSS representation</p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'>String</span><div class='sub-desc'><p>the color in rgb(##,##,##) representation</p>\n</div></li></ul></div></div></div><div id='method-getHashStyle' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-getHashStyle' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-getHashStyle' class='name expandable'>getHashStyle</a>( <span class='pre'></span> ) : String<strong class='deprecated signature' >deprecated</strong><strong class='private signature' >private</strong></div><div class='description'><div class='short'>Convert the color object into a HTML CSS representation ...</div><div class='long'><p>Convert the color object into a HTML CSS representation</p>\n        <div class='signature-box deprecated'>\n        <p>This method has been <strong>deprecated</strong> </p>\n        \n\n        </div>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'>String</span><div class='sub-desc'><p>the color in #RRGGBB representation</p>\n</div></li></ul></div></div></div><div id='method-getIdealTextColor' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-getIdealTextColor' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-getIdealTextColor' class='name expandable'>getIdealTextColor</a>( <span class='pre'></span> ) : <a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></div><div class='description'><div class='short'>Returns the ideal Text Color. ...</div><div class='long'><p>Returns the ideal Text Color. Useful for font color selection by a given background color.</p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></span><div class='sub-desc'><p>The <i>ideal</i> inverse color.</p>\n</div></li></ul></div></div></div><div id='method-getRed' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-getRed' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-getRed' class='name expandable'>getRed</a>( <span class='pre'></span> ) : Number</div><div class='description'><div class='short'>The red part of the color. ...</div><div class='long'><p>The red part of the color.</p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'>Number</span><div class='sub-desc'><p>the [red] part of the color.</p>\n</div></li></ul></div></div></div><div id='method-hash' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-hash' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-hash' class='name expandable'>hash</a>( <span class='pre'></span> ) : String</div><div class='description'><div class='short'>Convert the color object into a HTML CSS representation ...</div><div class='long'><p>Convert the color object into a HTML CSS representation</p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'>String</span><div class='sub-desc'><p>the color in #RRGGBB representation</p>\n</div></li></ul></div></div></div><div id='method-hex' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-hex' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-hex' class='name expandable'>hex</a>( <span class='pre'></span> )<strong class='private signature' >private</strong></div><div class='description'><div class='short'> ...</div><div class='long'>\n</div></div></div><div id='method-hex2rgb' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-hex2rgb' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-hex2rgb' class='name expandable'>hex2rgb</a>( <span class='pre'>hexcolor</span> )<strong class='private signature' >private</strong></div><div class='description'><div class='short'> ...</div><div class='long'>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>hexcolor</span> : Object<div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-int2hex' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-int2hex' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-int2hex' class='name expandable'>int2hex</a>( <span class='pre'>v</span> )<strong class='private signature' >private</strong></div><div class='description'><div class='short'> ...</div><div class='long'>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>v</span> : Object<div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-lighter' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.util.Color'>draw2d.util.Color</span><br/><a href='source/Color.html#draw2d-util-Color-method-lighter' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.util.Color-method-lighter' class='name expandable'>lighter</a>( <span class='pre'>fraction</span> ) : <a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></div><div class='description'><div class='short'>Make a color lighter. ...</div><div class='long'><p>Make a color lighter. The original color is unchanged.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>fraction</span> : Number<div class='sub-desc'><p>Darkness fraction between [0..1].</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.util.Color\" rel=\"draw2d.util.Color\" class=\"docClass\">draw2d.util.Color</a></span><div class='sub-desc'><p>Lighter color.</p>\n</div></li></ul></div></div></div></div></div></div></div>","name":"draw2d.util.Color","uses":[],"html_meta":{},"id":"class-draw2d.util.Color","tagname":"class","enum":null,"subclasses":[],"requires":[]});