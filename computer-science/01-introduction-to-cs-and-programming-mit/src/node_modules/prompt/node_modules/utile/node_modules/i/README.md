# inflect

customizable inflections for nodejs

## Installation

```bash
npm install i
```

## Usage

Require the module before using

```js
var inflect = require('i')();
```

All the below api functions can be called directly on a string

```js
inflect.titleize('messages to store') // === 'Messages To Store'
'messages to store'.titleize // === 'Messages To Store'
```

only if `true` is passed while initiating

```js
var inflect = require('i')(true);
```

### Pluralize

```js
inflect.pluralize('person'); // === 'people'
inflect.pluralize('octopus'); // === 'octopi'
inflect.pluralize('Hat'); // === 'Hats'
```

### Singularize

```js
inflect.singularize('people'); // === 'person'
inflect.singularize('octopi'); // === 'octopus'
inflect.singularize('Hats'); // === 'Hat'
```

### Camelize

```js
inflect.camelize('message_properties'); // === 'MessageProperties'
inflect.camelize('message_properties', false); // === 'messageProperties'
```

### Underscore

```js
inflect.underscore('MessageProperties'); // === 'message_properties'
inflect.underscore('messageProperties'); // === 'message_properties'
```

### Humanize

```js
inflect.humanize('message_id'); // === 'Message'
```

### Dasherize

```js
inflect.dasherize('message_properties'); // === 'message-properties'
inflect.dasherize('Message Properties'); // === 'Message Properties'
```

### Titleize

```js
inflect.titleize('message_properties'); // === 'Message Properties'
inflect.titleize('message properties to keep'); // === 'Message Properties to Keep'
```

### Demodulize

```js
inflect.demodulize('Message.Bus.Properties'); // === 'Properties'
```

### Tableize

```js
inflect.tableize('MessageBusProperty'); // === 'message_bus_properties'
```

### Classify

```js
inflect.classify('message_bus_properties'); // === 'MessageBusProperty'
```

### Foreign key

```js
inflect.foreign_key('MessageBusProperty'); // === 'message_bus_property_id'
inflect.foreign_key('MessageBusProperty', false); // === 'message_bus_propertyid'
```

### Ordinalize

```js
inflect.ordinalize( '1' ); // === '1st'
```

## Custom rules for inflection

### Custom plural

We can use regexp in any of these custom rules

```js
inflect.inflections.plural('person', 'guys');
inflect.pluralize('person'); // === 'guys'
inflect.singularize('guys'); // === 'guy'
```

### Custom singular

```js
inflect.inflections.singular('guys', 'person')
inflect.singularize('guys'); // === 'person'
inflect.pluralize('person'); // === 'people'
```

### Custom irregular

```js
inflect.inflections.irregular('person', 'guys')
inflect.pluralize('person'); // === 'guys'
inflect.singularize('guys'); // === 'person'
```

### Custom human

```js
inflect.inflections.human(/^(.*)_cnt$/i, '$1_count');
inflect.inflections.humanize('jargon_cnt'); // === 'Jargon count'
```

### Custom uncountable

```js
inflect.inflections.uncountable('oil')
inflect.pluralize('oil'); // === 'oil'
inflect.singularize('oil'); // === 'oil'
```

## Contributors
Here is a list of [Contributors](http://github.com/pksunkara/inflect/contributors)

### TODO

- More obscure test cases

__I accept pull requests and guarantee a reply back within a day__

## License
MIT/X11

## Bug Reports
Report [here](http://github.com/pksunkara/inflect/issues). __Guaranteed reply within a day__.

## Contact
Pavan Kumar Sunkara (pavan.sss1991@gmail.com)

Follow me on [github](https://github.com/users/follow?target=pksunkara), [twitter](http://twitter.com/pksunkara)
