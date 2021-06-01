# School of Computing &mdash; Year 4 Project Proposal Form


## SECTION A

|                     |                                         |
|---------------------|-----------------------------------------|
|Project Title:       | Nueral Activity Collection and Analysis |
|Student 1 Name:      | Cormac Duggan                           |
|Student 1 ID:        | 17100348                                |
|Student 2 Name:      | Sean Hammond                            |
|Student 2 ID:        | 17374356                                |
|Project Supervisor:  | Graham Healy                            |

> Ensure that the Supervisor formally agrees to supervise your project; this is only recognised once the
> Supervisor assigns herself/himself via the project Dashboard.
>
> Project proposals without an assigned
> Supervisor will not be accepted for presentation to the Approval Panel.

## SECTION B


### Introduction

For our Final Year Project, we hope to develop a piece of software that will assist in the analysis of online lectures and videos, in order to allow lecturers to create more effective learning environments. We aim to accomplish this by using an electroencephalography (EEG) device to measure focus. 

### Outline

For this project, we aim to develop an application for Windows that utilises the EEG capabilities of the Interaxon MUSE to allow users to record their brain activity and focus levels while watching an online lecture. The program would then allow users to view the graph created by their focus levels, with timestamps to show their focus at any given time during the viewing. Users could then upload their session to a SQL database, which could be accessed by a lecturer to view multiple sessions to gather the necessary information. This would allow lecturers to analyse where their students lose focus and allows them to determine when they should give the most vital information, and how that information should be presented.

### Background

From this project’s conception we knew that we were highly interested in using electroencephalography to aid in improving education and learning. Our original plan was to develop an application that allowed users to record their brain activity during a study session in order to determine their optimal study session lengths and break times, to become more efficient. As we discussed this idea further, we decided that we wanted to create something that could alleviate some of the difficulties associated with online learning through video lectures. 

After discussing this, we came up with the idea of developing a program that allows users to track focus levels while watching a given video, and map these levels to the specific time in the video to allow lecturers to determine at which stages students begin to lose focus. We also noted that this software would be useful for marketing and advertising purposes.

### Achievements

At the end of this project, the primary features we hope to have implemented are as following; the ability for users to record a session where their focus levels are monitored using an EEG device, the ability to display the graphed focus levels mapped to the time of the session and finally the ability to upload that session to an online database to allow a lecturer or video creator to view the recorded data.

Once we have completed the project, the skills we will have learned include UI creation in Java, signal processing in Python, and the accessing and storage of data in an online SQL database.

### Justification

There will be a variety of places our project could be utilized following its completion including schools, marketing teams, and anyone that wants to retain viewer attention on their piece of media. In schools lecturers could test out their online lecture/videos on a batch of students to see how well they retain attention during key aspects of their lectures. If they lose everyone’s attention right when they begin to talk about the most important concepts they can reorganize or restructure their way of conveying the topic. For marketing purposes teams could test their advertisements on potential customers and see if they are able to retain viewers up to and through when the product is placed on screen. If they lose viewers before the product appears they would know their ad is ineffective. From there specific examples of usage become more anecdotal than general cases. For example a youtuber may want to know where they lose viewers or how their jokes go over with the crowd. If viewers start looking away toward the recommended section at a certain point in the video, maybe that type of content isn’t what their community is looking for. 

### Programming language(s)

For our project we intend to use Python for the backend of the software and the logical interpretation of the EEG device readings. We are still undecided on how we will be storing data. It may be stored as an SQL database or we may store the readings data as a csv file. For our UI and application development we intend to use Java which will be a new challenge for the both of us.

### Programming tools / Tech stack

Our project will be utilizing Java and Python as well as SQL for our database. The python portion will be mostly contained in the back end, logic, and database interaction with the UI. The UI will be designed using Java, and our data will be recorded in a SQL database. All data will be obtained from an EEG device we will be using for the duration of the project. Should time permit, we will attempt to normalize the data so that other types of EEG devices could be used the same way ours is to work with our application.

### Hardware

To complete our project we will need some type of EEG nueral activity monitoring device. While typically not too easy to come by we have been loaned one by Graham Healy. He has been kind enough to loan his own EEG device to us for usage during the duration of the project.

### Learning Challenges

As neither of us has a ton of experience in building UI using Java that will most likely prove to be the biggest challenge for the pair of us. We will have to do a decent amount of research, testing, and tweaking to get things correct. Additionally, for the backend portion of our project, as we intend to use python we will likely need to find and understand multiple different libraries for using and interacting with EEG data sets. We will also need to work out our own way of interpreting the raw EEG data that is returned from the devices as well as attempt to normalize it for a variety of different forms of EEG devices should time and hardware permit. If we have everything completed we will also attempt to implement an application based video upload system rather than embedding them.

### Breakdown of work

#### Student 1

Cormac’s main responsibilities will focus on developing the front-end of the application, such as building the UI and developing navigation. 

We have agreed that due to our unfamiliarity with building UIs, especially in Java, we will likely split the workload between us, with Cormac taking the lead in its development and decision making.

We will also both work equally on the networking and database element for the storage of sessions, as we are similarly unfamiliar with SQL databases being used in a networking context.


#### Student 2

Sean’s primary responsibilities will be on developing the back-end of the project. He will develop the python scripts that will interpret the EEG signals and relay them to the Java application after being processed and normalised.

He will also assist in the development of the UI and database, as previously mentioned.

