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
 package za.co.sourlemon.acropolis.ems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import za.co.sourlemon.acropolis.ems.id.ID;
import za.co.sourlemon.acropolis.ems.id.Identifiable;

/**
 *
 * @author daniel
 */
public class Entity implements Identifiable<ID<Entity>>
{
    // keeps track of how many entities there are, for debug purposes

    private static int count = 0;
    public static final String DEFAULT_NAME = "entity";
    private String name; // for debug purposes
    private final Collection<EntityListener> listeners = new ArrayList<>();
    protected Collection<Entity> dependents = new ArrayList<>();
    private final Map<Class, Component> components = new HashMap<>();
    private final Map<Class<? extends Event>, List<Event>> events = new HashMap<>();
    private Pool source;
    private final ID<Entity> id = new ID<>();

    public Entity()
    {
        this(DEFAULT_NAME, null);
    }

    public Entity(Pool source)
    {
        this(DEFAULT_NAME, source);
    }

    public Entity(String name)
    {
        this(name, null);
    }

    public Entity(String name, Pool source)
    {
        this.source = source;
        this.name = name + "_" + (count++);
    }

    protected void returnToSource()
    {
        if (source != null)
        {
            source.returnEntity(this);
        }
    }

    public void addEntityListener(EntityListener l)
    {
        listeners.add(l);
    }

    public void removeEntityListener(EntityListener l)
    {
        listeners.remove(l);
    }

    public void addComponent(Component component)
    {
        Class componentClass = component.getClass();
        components.put(componentClass, component);

        for (EntityListener l : listeners)
        {
            l.componentAdded(this, componentClass);
        }
    }

    public void deepAddComponent(Component component)
    {
        addComponent(component);
        for (Entity dep : dependents)
        {
            dep.deepAddComponent(component);
        }
    }

    public void removeComponent(Class componentClass)
    {
        components.remove(componentClass);

        for (EntityListener l : listeners)
        {
            l.componentRemoved(this, componentClass);
        }
    }

    public void deepRemoveComponent(Class componentClass)
    {
        removeComponent(componentClass);
        for (Entity dep : dependents)
        {
            dep.deepRemoveComponent(componentClass);
        }
    }

    public void addDependent(Entity entity)
    {
        dependents.add(entity);
    }

    public Collection<Entity> getDependents()
    {
        return dependents;
    }

    public void removeDependent(Entity entity)
    {
        dependents.remove(entity);
    }

    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        return (T) components.get(componentClass);
    }

    public boolean hasComponent(Class componentClass)
    {
        return components.containsKey(componentClass);
    }

    public Component[] getAllComponents()
    {
        return components.values().toArray(new Component[0]);
    }

    public <E extends Event> void addEvent(E event)
    {
        event.setEntity(this);
        Class<? extends Event> eventClass = event.getClass();
        List<E> eventList = (List<E>) events.get(eventClass);
        if (eventList == null)
        {
            eventList = new ArrayList<>();
            events.put(eventClass, (List<Event>) eventList);
        }
        eventList.add(event);
    }

    public <E extends Event> List<E> getEventList(Class<E> eventClass)
    {
        List<E> eventList = (List<E>) events.get(eventClass);
        if (eventList == null)
        {
            eventList = new ArrayList<>();
            events.put(eventClass, (List<Event>) eventList);
        }
        return eventList;
    }
    
    public <E extends Event> void removeEvent(E event)
    {
        event.setEntity(null);
        Class<? extends Event> eventClass = event.getClass();
        List<? extends Event> eventList = events.get(eventClass);
        if (eventList == null)
        {
            return;
        }
        if (eventList.remove(event))
        {
            // NOTE: not sure about this for garbage collection issues.
//            if (eventList.isEmpty())
//            {
//                events.remove(eventClass);
//            }
        }
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public final ID<Entity> getId()
    {
        return id;
    }
    
}
