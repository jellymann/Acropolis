/*
 * The MIT License
 *
 * Copyright 2014 Daniel Smith <jellymann@gmail.com>.
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

package za.co.sourlemon.acropolis.tokyo;

import com.hackoeur.jglm.Quaternion;
import com.hackoeur.jglm.Vec3;
import za.co.sourlemon.acropolis.tokyo.components.Acceleration;
import za.co.sourlemon.acropolis.tokyo.components.State;
import za.co.sourlemon.acropolis.tokyo.components.Velocity;

/**
 *
 * @author Daniel Smith <jellymann@gmail.com>
 */
public class RK4Integrator implements Integrator
{

    /**
     * This method performs RK4 integration to approximate the position of an
     * entityState after a time-step. this method takes advantage of the fact
     * that a better approximation of a function can be reached if we use its
     * higher order derivatives. This results in much faster convergence than
     * Euler integration.
     *
     * @see <a
     * href="http://gafferongames.com/game-physics/integration-basics/">RK4
     * source 1<a/>
     * @see <a
     * href="http://stackoverflow.com/questions/1668098/runge-kutta-rk4-integration-for-game-physics">RK4
     * source 2<a/>
     *
     * @param state
     * @param velocity
     * @param acceleration
     * @param dt
     */
    @Override
    public void integrate(State state, Velocity velocity, Acceleration acceleration, float dt)
    {
        Derivative a = evaluate(state, velocity, acceleration, 0.0f, new Derivative());
        Derivative b = evaluate(state, velocity, acceleration, dt * 0.5f, a);
        Derivative c = evaluate(state, velocity, acceleration, dt * 0.5f, b);
        Derivative d = evaluate(state, velocity, acceleration, dt, c);

        state.pos = state.pos.add(d.ds.add(a.ds.add((b.ds.add(c.ds)).multiply(2))).multiply(dt / 6.0f));
        state.rot = state.rot.add(d.das.add(a.das.add((b.das.add(c.das)).scale(2))).scale(dt / 6.0f));
        velocity.v = velocity.v.add(d.dv.add(a.dv.add((b.dv.add(c.dv)).multiply(2))).multiply(dt / 6.0f));
        velocity.av = velocity.av.add(d.dav.add(a.dav.add((b.dav.add(c.dav)).scale(2))).scale(dt / 6.0f));
    }
    
    /**
     * this function evaluates a state in terms of the current time t and the
     * length of time for one time step dt. this function returns a derivative
     * object.
     *
     * @param state
     * @param velocity
     * @param acceleration
     * @param dt <i>time-step length</i>
     * @param derivative
     * @return
     */
    protected Derivative evaluate(final State state, final Velocity velocity,
            Acceleration acceleration,
            final float dt, final Derivative derivative)
    {
        //Vec3 pos = state.pos.add(derivative.dx.multiply(dt));
        Vec3 v = velocity.v.add(derivative.dv.multiply(dt));
        Quaternion av = velocity.av.add(derivative.dav.scale(dt));

        Derivative output = new Derivative();
        output.ds = v;
        output.dv = acceleration == null ? Vec3.VEC3_ZERO : acceleration.a;
        output.das = av;
        output.dav = acceleration == null ? Quaternion.QUAT_IDENT : acceleration.aa;

        return output;
    }
    
}
