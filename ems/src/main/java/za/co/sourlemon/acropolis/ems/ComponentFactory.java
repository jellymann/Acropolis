/*
 * Copyright (c) 2013 Daniel Smith <jellymann@gmail.com>.
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
 package za.co.sourlemon.acropolis.ems;

/**
 * Interface for creating components for the entity management system.
 * 
 * @author Daniel
 * @param <C> the type of component this factory produces
 * @param <R> the type of FactoryRequest this factory can accept
 */
public interface ComponentFactory<C extends Component, R extends FactoryRequest>
{
    /**
     * Creates an entity from the given factory request
     * @param request the factory request i.e. blueprints for creating the component
     * @return the created component
     * @throws FactoryException if there was any exception with creating the component
     */
    public C create(R request);
}
