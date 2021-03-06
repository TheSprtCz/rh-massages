/*******************************************************************************
 * Copyright (C) 2017 Petr Silling
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package net.rh.massages.health;

import com.codahale.metrics.health.HealthCheck;

import io.dropwizard.hibernate.UnitOfWork;
import net.rh.massages.core.Facility;
import net.rh.massages.db.FacilityDAO;

/**
 * Health check of the application. Is healthy when at least one
 * {@link Facility} exists inside the database.
 *
 * @author psilling
 * @since 1.0.0
 */
public class MassagesHealthCheck extends HealthCheck {

    private FacilityDAO facilityDAO; // Facility data access object

    /**
     * Constructor.
     *
     * @param facilityDAO {@link FacilityDAO} to work with
     */
    public MassagesHealthCheck(FacilityDAO facilityDAO) {
        this.facilityDAO = facilityDAO;
    }

    /**
     * Checks whether the application is healthy by checking database connection and
     * by checking if there is at least one existing {@link Facility}.
     */
    @UnitOfWork
    @Override
    protected Result check() throws Exception {
        if (facilityDAO.findAll().isEmpty()) {
            return Result.unhealthy("The application is unhealthy. No Facility found in the database.");
        }
        return Result.healthy();
    }
}
