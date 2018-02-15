'use strict';
define([], function() {

	return {
		WELCOME_CONTROLLER: 'A new and shiny controller has been made!',
		WELCOME_CONTROLLER_FOLLOWUP: 'This is the default view for your controlller. Change it (if you want, of course)!',
    PATTER: 'The field should only contain letters and numbers',
    SIZE: 'The field should have between {{max}} y {{min}} characters',
    LOGIN: 'Login',
    WELCOME_MESSAGE: 'Welcome to PawnsAtWar',
    USERNAME: 'Username',
    PASSWORD: 'Password',
    SIGN_UP: 'New? Sign Up!',
    REPEAT_PASSWORD: 'Repeat Password:',
    EMAIL: 'Email',
    REGISTER: 'Register',
    GO: ' GO',
    SPLIT: ' Split',
    MOVE: 'Move',
    ATTACK: ' Attack',
    MAP: 'Map',
    ARMY: 'Army',
    COMMERCE: 'Trade',
    MESSAGES: 'Messages',
    RANKING: 'Ranking',
    LOGOUT: 'Logout',
    AVAILABLE: ' Available',
    MANAGE : ' Manage',
    YES: ' Yes',
    NO: ' No',
    SUCCESS: 'Action successful',
    MERGE: ' Merge',
    ATTACKER: ' Attacker',
    DEFENDER: ' Defender',
    SURVIVORS: ' Survivors',
    KILLED: ' Killed',
    ORIGINAL: ' Original',
    DEMOLISH: ' Demolish',
    LEVEL_UP: ' Level up',
    CREATION_TIME: ' Creation time',
    AMOUNT: ' Amount',
    TRAIN: ' Train',
    BUILD: ' Build',
    COST: ' Cost',
    BUILDING: ' Building',
    LEVEL: ' Level',
    TIME: ' Time',
    TROOP_COST: ' Troop cost',
    WARRIOR: ' Warrior',
    ARCHER: ' Archer',
    HORSEMAN: ' Horseman',
    DESCRIPTION: 'Description',
    START_AGAIN: ' Start Again',
    TERRAIN_BELONGS: ' This terrain belongs to',
    WARNING_DEMOLISH: ' Are you sure? This action cannot be undone.',
    FOOD_PER_SECOND: ' Food per Second',
    GOLD_PER_SECOND: ' Gold per Second',
    BUILD_TIME_BONUS: ' Build time bonus',
    EXTRA_STORAGE: ' Extra storage space',
    NO_OFFERS: ' No offers',
    DIDNT_MAKE_OFFER: 'You didn\'t make any offer',
    GO_BACK: ' Go Back',
    NOT_AVAILABLE: ' We are sorry, the page you are looking for isn\'t available',
    INVALID_ADDRESS: 'Invalid Address!',
    PLAYER: 'Player',
    OFFERS: 'Offers',
    RECEIVES: 'Receives',
    ACCEPT: 'Accept',
    COMMERCE_ERROR: 'You don\'t have enough resources!',
    SAME_TYPE: 'Select resources of different types',
    REMOVE: 'Remove',
    MAKE_NEW_OFFER: 'Make new offer',
    MY_OFFERS: 'My Offers',
    CREATE: 'Create',
    CANCEL: 'Cancel',
    OFFER: 'Offer',
    RECEIVE: 'Receive',
    QUANTITY: 'Amount',
    MAILBOX: 'Mailbox',
    WRITE: 'Write',
    NEW_MESSAGE: 'New message',
    FROM: 'From',
    SUBJECT: 'Subject',
    MESSAGE: 'Message',
    NO_MESSAGES: 'No messages received',
    MESSAGE_UNREAD: 'Unread messages',
    MESSAGE_RECEIVED: 'Messages received',
    ACTION: 'Action',
    CHARACTER_REMAINING: 'Characters remaining',
    DELETE: 'Delete',
    ANSWER: 'Answer',
    MESSAGE_DESCRIPTION: 'Message description',
    LONG_MESSAGE: 'Message too long',
    READ_MESSAGE: 'Read messages',
    CREATE_MESSAGE: 'Create message',
    ANSWER_MESSAGE: 'Answer message',
    SENT_MESSAGE: 'Sent messages',
    NO_USER: 'No user selected',
    NO_SUBJECT: 'No subject',
    SEARCHING: 'Searching...',
    NO_RESULT: 'No user found',
    NO_MESSAGE: 'Message is required',
    INVALID_USER: 'The user doesn\'t exists',
    MESSAGE_TO_SELF: 'Can\'t send a message to yourself',
    TO: 'To:',
    MAIL_PLACEHOLDER: 'Enter player name',
    ADVICE: ' We\'ll never share your email with anyone else.',
    SEND_MESSAGE: 'Send message',
    MESSAGE_SENT: ' Your message has been sent!',
    DEFENDER_WON: ' Unsuccessful attack, defender won',
    ATTACKER_WON: ' Successful attack!! The building was destroyed',
    DRAW: ' Draw both armies where killed',
    NO_DEFENDER_ARMY: ' Your army didn\'t encounter any resistance, the building was destroyed',
    NO_ARMY: ' You don\'t have any army',
    RANK: ' Rank',
    SCORE: ' Score',
    ALERT_BUILD: ' Building {{name}} on {{x}},{{y}}',
    ALERT_UPGRADE: ' Upgrading {{name}} on {{x}},{{y}}',
    ALERT_RECRUIT: ' Training {{count}} {{name}} on {{x}},{{y}}',
    ALERT_ATTACK: 'Attacking {{x}},{{y}}',
    ALERT_SPLIT: 'Spliting to {{x}},{{y}}',
    ALERT_MOVE: 'Moving to {{x}},{{y}}',
    ALERT_MERGE: 'Merging on {{x}},{{y}}',
    ALERT_ATTACK_NOTIFICATION: 'Someone is attacking you on {{x}},{{y}}',
    ALERT_RETURN: 'Your troops are returning from {{x}}, {{y}}',
    ENDS_IN: 'Ends in',
    SECONDS: 'seconds',
    UPGRADE: 'Upgrading',
    RECRUIT: 'Training',
    GAME_OVER: 'Your castle was destroyed',
    ARMY_BUSY: 'This Army is Busy',
    INVALID_PARAM: 'Invalid parameters',
    INVALID_ARMY: 'Invalid army',
    INVALID_POSITION: 'Invalid position',
    INVALID_TROOP_TYPE: 'Invalid troop type',
    INVALID_TROOP_AMOUNT: 'Invalid troop amount',
    NOT_YOUR_ARMY: 'That army doesn\'t belong to you',
    ARMY_DONT_EXIST: 'That army doesn\'t exist',
    NOT_BUILDING_IN_POSITION: 'There isn\'t any building in that position',
    ATTACK_SELF_BUILDING: 'Can\'t attack one of you own buildings',
    ATTACK_EMPTY_TERRAIN: 'Can\'t attack empty terrain',
    CANT_ATTACK_CASTLE: 'Can\'t attack castle before all other buildings are destroyed',
    CANT_RECRUIT_TROOP: 'Can\'t recruit troops in an empty terrain',
    CANT_BUILD: 'Can\'t construct this building in this position',
    NOT_YOUR_TERRAIN: 'This terrain doesn\'t belong to you',
    CANT_LEVEL_UP: 'Can\'t level up a terrain',
    MAX_LEVEL: 'Can\'t level up, maximum level reached',
    PASSWORD_DONT_MATCH: 'Don\'t match',
    USER_ALREADY_EXIST: 'The username already exists',
    WRONG_USER: 'The user with that password doesn\'t exist',
    ALREADY_LOGOUT: 'There is no account to logout',
    ARMY_IN: 'Army in {{x}}, {{y}}',
    NO_FOOD: 'Not enough food',
    NO_GOLD: 'Not enough gold',
    NOT_ENOUGH_RESOURCES: 'Not enough resources',
    CANT_ACCEPT_SELF_OFFER: 'Can\'t accept your own offer',
    NO_USER_IN_POSITION: 'No user in that position, castle already destroyed',
    TROOPS_TRAINED: 'Troops trained',
    NO_CASTLE: 'The user does not have a castle',
    NO_PLACE: 'We couldn\'t find a place for your castle',
    DESC_EMPTY: 'This is an area available for building construction, To build a castle, the terrain mustn\'t have an owner, and be 3 tiles away from a terrain with an owner',
    DESC_CASTLE : 'This building is a castle. Leveling up will increase the storage capacity. If a player is left with no castles he loses.',
    DESC_ARCHERY : 'This building is an archery where archers can be recruited.',
    DESC_BARRACKS : 'This building is a barracks where warriors can be recruited.',
    DESC_GOLD : 'This building generates gold.',
    DESC_TERR_GOLD : 'This is an area available for gold mine construction.',
    DESC_MILL : 'This building generates food.',
    DESC_STABLE : 'This building is a stable where knights can be recruited.',
    BUILDING_BEING_CONSTRUCTED: 'A building is being constructed in this position',
    EMPTY : ' Empty',
    CASTLE : ' Castle',
    ARCHERY : ' Archery',
    BARRACKS : ' Barracks',
    GOLD : ' Gold Mine',
    TERR_GOLD : ' Gold Terrain',
    MILL : ' Mill',
    STABLE : ' Stable',
    TIME_SECONDS: 'Time (s)',
    UPGRADES: 'Upgrades',
    POSITION: 'Position',
    TACTICS:  'Tactic View',
    SEND_TO: 'Send to',
    TYPE: 'Type',
    TROOPS: 'Troops',
    TIME_UNIT: 'Training time (s)'
    };
});
