#JavaHasen 1.2

A simple simulation of different creatures on a field eating, spawn children and die at some point.<br>
This project is inspired by a lecture i recently attended.<br>
It was presented in ruby, which made me want to show that java is better.<br>
So I started to rebuild the simple simulation and add a little bit of falvor to it.<br>

![Screenshot](https://raw.github.com/futjikato/HAW-PR1-FuechseUndHasenJava/master/resources/v1.2_menu.png "Screenshot of the new menu screen")
![Screenshot](https://raw.github.com/futjikato/HAW-PR1-FuechseUndHasenJava/master/resources/v1.2_simulation.png "Screenshot of a paused simulation")

### What´s new in 1.2

* Removed Slick2D Library
* Added TWL lib
* New Menu Screen
* New Pause Button in simulation
* Decity scroller on menu

## How to get it running

This example uses [LWJGL](http://www.lwjgl.org) as the rendering engine.
Its really simple [to make this work in Eclispe](http://www.lwjgl.org/wiki/index.php?title=Setting_Up_LWJGL_with_Eclipse).
In addition you need to add the lwjgl_util.jar as an external lib to make the 3d stuff work :-)<br>
LWJGL Copyright (c) 2002-2007 Lightweight Java Game Library Project<br>
All rights reserved.

I´m also using [TWL](http://twl.l33tlabs.org/) ( updated in 1.2 so watch out here ! ) for the GUI.
It´s included in the repository and needs to be added as an external jar, too.<br>
TWL Copyright (c) 2008-2009, Matthias Mann<br>
All rights reserved.

## Controls

You can move the camera with the arrow keys 

UP - Zoom In<br>
Down - Zoom Out<br>
Left - Move camera to the left<br>
Right - Move the camera to the right

There might be some mouse control right now but it is buggy and ugly.

## ToDo´s

* Add models for creatures
* Create more creatures
* Improve camera movement and stuff....

## License ( [MIT](http://www.opensource.org/licenses/MIT) )

Copyright (c) 2012 Moritz Spindelhirn

   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.