# Simulation of a Material Recycling System
This is a Java application that consists of a control station and multiple recycling machines.

**Control station**: allows administrator to log in and perform control tasks such as add or activate a recycling machine, view usage statistics of 

machines in the system in the form of a bar chart, etc.

**Recycling machine**: allows users to deposit recycled items. Amount due to user is calculated based on item material and weight.  Machine 

automatically shuts down when full.


## User Stories

#### The following functionality is completed:

###### Control station:
* Administrator can log in the system
* Administrator can add then activate a new Recycling machine
* Administrator can remove a machine from the system
* Administrator can check current status of each machine, such as its functional mode, curent weight and amount of cash, last time emptied
* Administrator can get usage statistics of each machine 

###### Recycling machine:
* User can insert items in the designated receptacle
* User can choose to get paid with cash or coupon

#### The following features are implemented:
* Maintain consistency between data storage and UI using *MVC (Model-View-Controller)* model
* Create GUI for one Control station and one Recycling machine using Java Swing API
* Persist the data with Java Serialization API
* Implement automatic signal to Control station when a machine is full or out of money with *Observer* design pattern
* Appropriately able/disable GUI control buttons according to different states of the machine object 


## Video Walkthrough 
