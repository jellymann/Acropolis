/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 #version 120

uniform sampler2D tex;

uniform mat4 projection;
uniform mat4 world;
uniform mat4 view;

uniform vec3 sun;
uniform vec3 eye;

uniform vec3 colour;
uniform float opacity;

varying vec3 g_normal;
varying vec3 g_position;

void main()
{
    //float u = gl_TexCoord[0].x;
    //float v = 1.0 - gl_TexCoord[0].y;


    float ia = 0.3f;
    float id = 0.5f;
    float is = 1.0f;
    float s = 100.0f;

    vec3 v = normalize(eye-g_position);
    vec3 l = normalize(sun);
    vec3 r = normalize(reflect(l,g_normal));

    float ip = ia + max(dot(l,g_normal),0)*id + pow(max(dot(r,v),0),s)*is;

    gl_FragColor = vec4(ip * colour,opacity);
}

