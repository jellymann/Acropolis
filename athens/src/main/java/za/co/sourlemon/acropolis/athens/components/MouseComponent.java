/*
 * The MIT License
 *
 * Copyright 2013 Daniel Smith <jellymann@gmail.com>.
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
package za.co.sourlemon.acropolis.athens.components;

import com.hackoeur.jglm.Vec3;
import org.lwjgl.input.Mouse;
import za.co.sourlemon.acropolis.ems.Component;

/**
 *
 * @author daniel
 */
public class MouseComponent extends Component
{
    public boolean[] pressed = new boolean[Mouse.getButtonCount()];
    public boolean[] released = new boolean[Mouse.getButtonCount()];
    public boolean[] down = new boolean[Mouse.getButtonCount()];
    
    public int x = 0, y = 0;
    public int dx = 0, dy = 0;
    public float nx = 0, ny = 0;
    
    public Vec3 near = Vec3.VEC3_ZERO, far = Vec3.VEC3_ZERO;
}
