
/*
 * Copyright (c) 2002-2020, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.appointment.modules.desk.business;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

/**
 * This is the business class for the object AppointmentDesk
 */
public class AppointmentDesk implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nIdAppointmentDesk;

    private int _nIdForm;
    @NotNull( message = "#i18n{portal.validation.message.notEmpty}" )
    private LocalDate _day;
    private int _nDeskNumber;
    @NotNull( message = "#i18n{portal.validation.message.notEmpty}" )
    private LocalTime _startClosing;
    @NotNull( message = "#i18n{portal.validation.message.notEmpty}" )
    private LocalTime _endClosing;

    /**
     * Returns the IdAppointmentDesk
     * 
     * @return The IdAppointmentDesk
     */
    public int getIdAppointmentDesk( )
    {
        return _nIdAppointmentDesk;
    }

    /**
     * Sets the IdAppointmentDesk
     * 
     * @param nId
     *            The IdAppointmentDesk
     */
    public void setIdAppointmentDesk( int nIdAppointmentDesk )
    {
        _nIdAppointmentDesk = nIdAppointmentDesk;
    }

    /**
     * Returns the IdForm
     * 
     * @return The IdForm
     */
    public int getIdForm( )
    {
        return _nIdForm;
    }

    /**
     * Sets the IdForm
     * 
     * @param nIdForm
     *            The IdForm
     */
    public void setIdForm( int nIdForm )
    {
        _nIdForm = nIdForm;
    }

    /**
     * Returns the Day
     * 
     * @return The Day
     */
    public LocalDate getDay( )
    {
        return _day;
    }

    /**
     * Sets the Day
     * 
     * @param dateDay
     *            The Day
     */
    public void setDay( LocalDate dateDay )
    {
        _day = dateDay;
    }

    /**
     * Returns the Day date sql
     * 
     * @return The Day sql date
     */
    public Date getSqlDateDay( )
    {
        return Date.valueOf( _day );
    }

    /**
     * Sets the Day
     * 
     * @param dateDay
     *            The Day
     */
    public void setSqlDateDay( Date dateDay )
    {
        _day = dateDay.toLocalDate( );
    }

    /**
     * Returns the DeskNumber
     * 
     * @return The DeskNumber
     */
    public int getDeskNumber( )
    {
        return _nDeskNumber;
    }

    /**
     * Sets the DeskNumber
     * 
     * @param nDeskNumber
     *            The DeskNumber
     */
    public void setDeskNumber( int nDeskNumber )
    {
        _nDeskNumber = nDeskNumber;
    }

    /**
     * Returns the StartClosing
     * 
     * @return The StartClosing
     */
    public LocalTime getStartClosing( )
    {
        return _startClosing;
    }

    /**
     * Sets the StartClosing
     * 
     * @param dateStartClosing
     *            The StartClosing
     */
    public void setStartClosing( LocalTime dateStartClosing )
    {
        _startClosing = dateStartClosing;
    }

    /**
     * Returns the EndClosing
     * 
     * @return The EndClosing
     */
    public LocalTime getEndClosing( )
    {
        return _endClosing;
    }

    /**
     * Sets the EndClosing
     * 
     * @param dateEndClosing
     *            The EndClosing
     */
    public void setEndClosing( LocalTime dateEndClosing )
    {
        _endClosing = dateEndClosing;
    }

    /**
     * Get the starting time of the time closing (in sql time)
     * 
     * @return the starting time
     */
    public Time getStartingTimeSqlTime( )
    {
        Time time = null;
        if ( _startClosing != null )
        {
            time = Time.valueOf( _startClosing );
        }
        return time;
    }

    /**
     * Set the starting time of the time closing
     * 
     * @param startingTime
     *            the starting time (in sql time)
     */
    public void setSqlStartingTime( Time startingTime )
    {
        if ( startingTime != null )
        {
            _startClosing = startingTime.toLocalTime( );
        }
    }

    /**
     * Get the ending time in sql time
     * 
     * @return the ending time in sql time
     */
    public Time getEndingTimeSqlTime( )
    {
        Time time = null;
        if ( _endClosing != null )
        {
            time = Time.valueOf( _endClosing );
        }
        return time;
    }

    /**
     * Set the ending time of the time closing
     * 
     * @param endingTime
     *            the ending time (in sql time format)
     */
    public void setSqlEndingTime( Time endingTime )
    {
        if ( endingTime != null )
        {
            _endClosing = endingTime.toLocalTime( );
        }
    }
}
