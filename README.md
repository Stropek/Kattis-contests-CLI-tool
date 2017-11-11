# Kattis Competition CLI

### Overview 
Main purpose of _Kattis Competition CLI_ is to automate the process of contest creation on http://open.kattis.com

### Prerequisites
Install [java](https://www.java.com/en/download/help/download_options.xml).

### Usage
For default configuration to work you'll need to create a _.kattis_ configuration file under your _HOME_ location. \
You can check your _token_ by logging in to [kattis](https://open.kattis.com) and visiting [this](https://open.kattis.com/download/kattisrc) page.

###### sample _.kattis_ file
```
username: valid_open_kattis_username
token: valid_token
```

#### Options

##### Flags
Name  | Short Name | Purpose
------------- | ------------- | -------------
--help  | -h | Display CLI help
--verbose | -v | Use verbose logging
--isOpen | -o | Set contest registration type to 'open'

##### Parameters
Name  | Short Name | Default Value | Purpose | 
------------------------ | ------------------------ | ------------------------ | ------------- 
--name  | -n | AvaSE-{month}-{year} | Contest name. {month} & {year} - for the next Saturday.
--startDate | -d | Next Saturday, 11:59PM | Date and time that the contest starts.
--user | -u | From _.kattis_ file (_HOME_ dir) | Name of the user that's going to be contest's author.
--token | -t | From _.kattis_ file (_HOME_ dir) | Token of the user that's going to be contest's author.
--setting | -s | | File to use instead of _.kattis_ file in _HOME_ path.
--teams | | configuration/teams.kattis | File with _teams_ defined.
--problems | | 5 | Number of problems to include in the contest.
--min-level | -m | 2.5 | Minimum difficulty level of problems.
--duration | -r | 168 | Duration of the contest (in hours).

###### sample _teams.kattis_ file
```
# ignored comment
team_1: team_member_1_1, team_member_1_2
team_2: team_member_2_1
# team_member has to be either a valid Open Kattis user name or email.
```

### License
MIT
