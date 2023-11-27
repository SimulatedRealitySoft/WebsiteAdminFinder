<p align="center">
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/icon.jpg" height="92" />
</p>
<h1 align="center">Website Admin Finder</h1>

![License](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)

Website Admin Finder le permite auditar la seguridad de los sitios web en busca de paneles de administración automáticamente.

![License](https://img.shields.io/badge/License-GPL%2F3.0-orange?style=flat)
[![Pull requests](https://img.shields.io/github/issues-pr/SimulatedRealitySoft/WebsiteAdminFinder.svg?style=flat)](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/pulls)
![MinSdk](https://img.shields.io/badge/Minimum%20SDK-21%20(Lollipop)-839192?style=flat&logo=android&logoColor=green)
[![Latest tag](https://img.shields.io/github/tag/SimulatedRealitySoft/WebsiteAdminFinder.svg?style=flat)](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/tags)
![Permissions](https://img.shields.io/badge/permissions-3-brightgreen?style=flat&logo=iconify&logoColor=green)

## LEEME Traducción
- <b>Español</b>
- [English](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/README.md)
### Acerca de

**Website Admin Finder** Es una aplicación liviana para Android que le permite auditar la seguridad de los sitios web en busca del panel de administración automáticamente. Se basa en solicitudes de fuerza bruta para encontrar el panel.

No encontré una aplicación o software similar, necesitaba tener este tipo de herramientas a mi alcance, así que pensé en desarrollarla, y por qué no, hacerla pública, gratuita y de código abierto.

### Características
- <b>Liviana</b>: No utiliza demasiados recursos y se evitó el uso de demasiados vectores para conservar el tamaño y mejorar el rendimiento.<br /><br />
- <b>Escaneo múltiple</b>: Puede importar su propia WordList de sitios web para escanear más de un sitio a la vez.<br /><br />
- <b>Log en tiempo real</b>: Muestra un registro en tiempo real para notificarle que ha encontrado un sitio web.<br /><br />
- <b>Servicio en el fondo</b>: ¿No quieres tener la aplicación en primer plano o esperar? No hay problema; Con las notificaciones se le notificarán los resultados.<br /><br />
- <b>Persistencia</b>: ¿Olvidaste qué sitio web escaneaste o qué paneles de administración pudiste encontrar? No te preocupes, la aplicación mantiene el registro y también guarda los resultados positivos.<br /><br />
- <b>Tiempo exacto</b>: Si necesita saber la hora exacta de cada solicitud, la aplicación puede guardar la hora, los minutos, los segundos e incluso los milisegundos exactos.

<summary><h3 style="display: inline">Capturas de pantalla</h3></summary>

<p align="center">
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/phoneScreenshots/01_en-US.jpg" height="500"/>
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/phoneScreenshots/02_en-US.jpg" height="500"/>
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/phoneScreenshots/03_en-US.jpg" height="500"/>
</p>

### Crear WorldList

Para analizar varios sitios al mismo tiempo, puede crear una WorldList que contenga la dirección URL de los sitios web que desea analizar. La "sintaxis" es simple, primero crea un archivo txt en el almacenamiento, ábrelo con cualquier editor de texto y asegúrate de apilar los enlaces uno encima del otro.

Un simple ejemplo podría ser:

*WordList.txt*

<code>https://example1.com
https://example2.net
https://ejemplo.xyz
</code>

### Atención

**Ten en cuenta** que la velocidad del análisis no se basará en la lógica de programación de la aplicación. El 40% se basa en su velocidad de Internet y el 60% se basa en los servidores del sitio web. Que yo sepa, no hay nada que podamos hacer con eso.

### Creditos

Desarrollado por **Diego Calveyra**.<br/>
2023.

### Contacto

Puedes contactarnos en [SimulatedRealitySoft@gmail.com](mailto:[simulatedrealitysoft@gmail.com).
Siéntase libre de enviar sus preguntas, sugerencias o temas relacionados. Estaremos encantados de responder a todo tipo de mensajes. Tenga en cuenta que ignoraremos los mensajes vulgares, dañinos o de odio.

##### mensaje a desconocido
Nunca entendi por qué.

## Descargas

Por el momento puedes descargar el proyecto compilado y firmado (****APK****) desde *GitHub*.

[<img src="https://raw.githubusercontent.com/Unknown-60/Unknown-60.github.io/main/assets/get-it-on-github.png"
     alt="Get it on GitHub"
     height="80">](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/releases/latest)

### Licencia

> Website Admin Finder (c) 2023 Diego Calveyra
> 
> This is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
> 
> This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
> 
> You should have received a copy of the GNU General Public License along with this app. If not, see https://www.gnu.org/licenses/.
