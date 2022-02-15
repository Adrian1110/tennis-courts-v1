# Tennis Courts - Challenge

Small ideas of improvements:

- add liquibase
- add some profiles
- add some log aspects
- maybe a docker compose file to check all : service, database, database client
- small diagram with database tables

# Tasks that has been implemented:
-  As a User I want to be able to book a reservation for one or more tennis court at a given date schedule
-  As a User I want to be able to cancel a reservation 
-  As a User I want to be able to reschedule a reservation 
-  As a Tennis Court Admin, I want to be able to Create/Update/Delete/Find by id/Find by name/List all the guests
-  As a Tennis Court Admin, I want to charge a reservation deposit of $10 to the user, charged per court, which is refunded upon completion of their match, so that Users don’t abuse my schedule
-  As a Tennis Court Admin, I want to refund the reservation deposit if the user has cancelled or rescheduled their reservation more than 24 hours in advance
-  As a Tennis Court Admin, I want to keep 25% of the reservation fee if the User cancels or reschedules between 12:00 and 23:59 hours in advance, 50% between 2:00 and 11:59 in advance, and 75% between 0:01 and 2:00 in advance
-  Implement all the missing Restful swagger statements and the API paths to the controllers
-  Check why rescheduleReservation in the class ReservationService.java is not working correctly and fix it
