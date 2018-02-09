'use strict';
define([], function() {

  return {
    WELCOME_CONTROLLER: 'A new and shiny controller has been made!',
    WELCOME_CONTROLLER_FOLLOWUP: 'This is the default view for your controlller. Change it (if you want, of course)!',
    PATTER: 'El campo debería tener solamente letras y números',
    SIZE: 'El campo debería tener entre {{min}} y {{max}} caracteres',
    LOGIN: 'Inicia Sesión',
    WELCOME_MESSAGE: 'Bienvenido a PawnsAtWar',
    USERNAME: 'Usuario',
    PASSWORD: 'Contraseña',
    SIGN_UP: 'Eres nuevo? Regístrate!',
    REPEAT_PASSWORD: 'Repetir Contraseña',
    EMAIL: 'Email',
    REGISTER: 'Registrarse',
    GO: ' IR',
    SPLIT: ' Separar',
    ATTACK: ' Atacar',
    MAP: 'Mapa',
    ARMY: 'Ejército',
    COMMERCE: 'Comercio',
    MESSAGES: 'Mensajes',
    RANKING: 'Ranking',
    LOGOUT: 'Salir',
    AVAILABLE: 'Disponible',
    MANAGE : 'Dirigir',
    YES : 'Si',
    NO : 'No',
    MERGE: 'Juntar',
    ATTACKER: 'Atacante',
    DEFENDER: ' Defensor',
    SURVIVORS: 'Sobrevivientes',
    KILLED: 'Muertos',
    ORIGINAL: 'Originales',
    DEMOLISH: 'Demoler',
    LEVEL_UP: 'Subir de nivel',
    CREATION_TIME: 'Tiempo de creación',
    AMOUNT: 'Cantidad',
    TRAIN: 'Entrenar',
    BUILD: 'Construir',
    COST: 'Costo',
    BUILDING: 'Edificio',
    LEVEL: 'Nivel',
    TIME: 'Tiempo',
    TROOP_COST: 'Costo unidad',
    WARRIOR: 'Guerrero',
    ARCHER: 'Arquero',
    HORSEMAN: 'Caballero',
    DESCRIPTION: 'Descripción',
    START_AGAIN: ' Volver a empezar',
    TERRAIN_BELONGS: 'Este terreno pertenece a',
    WARNING_DEMOLISH: 'Estas seguro? Esta acción no se puede deshacer',
    FOOD_PER_SECOND: 'Comida por segundo',
    GOLD_PER_SECOND: 'Oro por segundo',
    BUILD_TIME_BONUS: 'Bonificación tiempo construcción',
    EXTRA_STORAGE: 'Lugar extra de almacenamiento',
    NO_OFFERS: 'No hay ofertas',
    DIDNT_MAKE_OFFER: 'No hizo ninguna oferta',
    GO_BACK: 'Volver',
    NOT_AVAILABLE: 'Lo sentimos, pero la página que usted busca no está disponible.',
    INVALID_ADDRESS: 'Dirección Inválida!',
    PLAYER: 'Jugador',
    OFFERS: 'Ofrece',
    RECEIVES: 'Recibe',
    ACCEPT: 'Aceptar',
    COMMERCE_ERROR: 'No tienes suficientes recursos!',
    SAME_TYPE: 'Selecciona dos recursos diferentes',
    REMOVE: 'Remover',
    MAKE_NEW_OFFER: 'Realizar nueva oferta',
    MY_OFFERS: 'Mis Ofertas',
    CREATE: 'Crear',
    CANCEL: 'Cancelar',
    OFFER: 'Ofrecer',
    RECEIVE: 'Recibir',
    QUANTITY: 'Cantidad',
    FROM: 'De',
    SUBJECT: 'Asunto',
    MESSAGE: 'Mensaje',
    NO_MESSAGE: 'No hay mensajes',
    MESSAGE_RECEIVED: 'Mensajes recibidos',
    MESSAGE_UNREAD: 'Mensajes sin leer',
    ACTION: 'Accion',
    CHARACTER_REMAINING: 'Caracteres restantes',
    DELETE: 'Borrar',
    MESSAGE_DESCRIPTION: 'Descripción del mensaje',
    LONG_MESSAGE: 'Mensaje demasiado largo',
    READ_MESSAGE: 'Mensajes leidos',
    CREATE_MESSAGE: 'Escribir mensaje',
    ANSWER_MESSAGE: 'Responder mensaje',
    TO: 'Para:',
    MAIL_PLACEHOLDER: 'Ingresa nombre jugador',
    ADVICE: 'No compartiremos tu email con nadie.',
    SEND_MESSAGE: 'Enviar mensaje',
    MESSAGE_SENT: 'Tu mensaje se ha enviado con éxito!',
    DEFENDER_WON: 'Ataque no exitoso, el defensor ganó',
    ATTACKER_WON: 'Ataque exitoso!! El edificio fue destruido',
    DRAW: 'Empate los dos ejércitos fueron destruidos',
    NO_DEFENDER_ARMY: 'Tu ejército no encontró ninguna resistencia, el edificio fue destruido',
    NO_ARMY: 'No tienes ejércitos',
    RANK: 'Posición',
    SCORE: 'Puntaje',
    ALERT_BUILDING: 'Construyendo {{name}} en {{x}},{{y}}',
    ALERT_UPGRADING: 'Mejorando {{name}} en {{x}},{{y}}',
    ALERT_TRAINING: 'Entrenando {{count}} {{name}} en {{x}},{{y}}',
    ALERT_ATTACKING: 'Atacando {{x}},{{y}}',
    ALERT_SPLIT: 'Separando ejercito hacia {{x}},{{y}}',
    ALERT_MOVE: 'Moviendo hacia {{x}},{{y}}',
    ALERT_MERGE: 'Juntando ejercitos en {{x}},{{y}}',
    ALERT_NOTIFY_ATTACK: '{{name}} te esta atacando en {{x}},{{y}}',
    ENDS_IN: 'Finaliza en',
    SECONDS: 'segundos',
    UPGRADE: 'Mejorando',
    RECRUIT: 'Entrenando',
    GAME_OVER: 'Su castillo fue destruido. Fin del juego. Por favor, ingrese con otro usuario',
    INVALID_PARAM: 'Parámetros inválidos',
    INVALID_ARMY: 'Ejército inválido',
    INVALID_POSITION: 'Posición inválida',
    INVALID_TROOP_TYPE: 'Tipo de tropa inválida',
    INVALID_TROOP_AMOUNT: 'Cantidad de tropa inválida',
    NOT_YOUR_ARMY: 'Este ejército no te pertenece',
    ARMY_DONT_EXIST: 'Este ejército no existe',
    NOT_BUILDING_IN_POSITION: 'No hay ningún edificio en esa posición',
    ATTACK_SELF_BUILDING: 'No puedes atacar uno de tus edificios',
    ATTACK_EMPTY_TERRAIN: 'No puedes atacar un territorio vacío',
    CANT_ATTACK_CASTLE: 'No se puede atacar un castillo antes de haber destruido todos sus edificios',
    CANT_RECRUIT_TROOP: 'Un terreno vacío no puede reclutar tropas',
    CANT_BUILD: 'No se puede construir este edificio en este lugar',
    NOT_YOUR_TERRAIN: 'Este terreno no te pertenece',
    CANT_LEVEL_UP: 'No se puede subir de nivel un terreno vacío',
    MAX_LEVEL: 'No se puede subir de nivel. Nivel máximo alcanzado',
    PASSWORD_DONT_MATCH: 'No son iguales',
    USER_ALREADY_EXIST: 'El nombre de usuario ya existe',
    WRONG_USER: 'La contraseña o el usuario son incorrectos',
    ALREADY_LOGOUT: 'Ya cerraste sesión',
    NO_FOOD: 'No hay suficiente comida',
    NO_GOLD: 'No hay suficiente oro',
    NOT_ENOUGH_RESOURCES: 'No tienes suficientes recursos',
    NO_USER_IN_POSITION: 'No existe usuario en esa posición, el castillo ya fue destruido',
    TROOPS_TRAINED: 'Tropas entrenadas',
    NO_CASTLE: 'El usuario no tiene castillo',
    NO_PLACE: 'No pudimos encontrar lugar para tu castillo',
    DESC_EMPTY: 'Este es un territorio en el cual puedes construir edificios.',
    DESC_CASTLE : 'Este edificio es un castillo. Aumentarlo de nivel permite almacenar mas recursos. Si un jugador se queda sin castillos pierde.',
    DESC_ARCHERY : 'Este edificio es una arquería. Aquí se pueden entrenar arqueros.',
    DESC_BARRACKS : 'Este edificio es un cuartel. Aquí se pueden entrenar guerreros.',
    DESC_GOLD : 'Este edificio genera oro.',
    DESC_TERR_GOLD : 'Terreno propicio para construir una mina de oro.',
    DESC_MILL : 'Este edificio genera comida.',
    DESC_STABLE : 'Este edificio es un establo. Aquí se pueden entrenar caballeros.',
    BUILDING_BEING_CONSTRUCTED: 'Ya hay un edificio siendo construido en esta posición',
    EMPTY : 'Libre',
    CASTLE : 'Castillo',
    ARCHERY : 'Arquería',
    BARRACKS : 'Cuartel',
    GOLD : 'Mina de oro',
    TERR_GOLD : 'Terreno de oro',
    MILL : 'Molino',
    STABLE : 'Establo'
  };
});
