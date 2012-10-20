#JavaHasen

A simple simulation of different creatures on a field eating, spawn children and die at some point.<br>
This project is inspired by a lecture i recently attended.<br>
It was presented in ruby, which made me want to show that java is better.<br>
So I started to rebuild the simple simulation and add a little bit of falvor to it.<br>

![Screenshot](https://raw.github.com/futjikato/HAW-PR1-FuechseUndHasenJava/master/resources/javahasen_screen_mac.png "Screenshot of a running simulation")

## How to get it running

This example uses [LWJGL](http://www.lwjgl.org) as the rendering engine.
Its really simple [to make this work in Eclispe](http://www.lwjgl.org/wiki/index.php?title=Setting_Up_LWJGL_with_Eclipse).
In addition you need to add the lwjgl_util.jar as an external lib to make the 3d stuff work :-)

I´m also using the [Slick-Util](http://www.lwjgl.org/wiki/index.php?title=Slick-Util_Library_-_Introduction) library to load textures.
It´s included in the repository and needs to be added as an external jar, too.

## Controls

You can move the camera with the arrow keys 

UP - Zoom In<br>
Down - Zoom Out<br>
Left - Move camera to the left<br>
Right - Move the camera to the right

There might be some mouse control right now but it is buggy and ugly.

## ToDo´s

* Improve start menu ( Top priority ! )
* Create more creatures
* Add textures to simulation
* Add options to configure simulation parameters
* Improve camera movement and stuff....

## License ( [MIT](http://www.opensource.org/licenses/MIT) )

Copyright (c) 2012 Moritz Spindelhirn

   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.