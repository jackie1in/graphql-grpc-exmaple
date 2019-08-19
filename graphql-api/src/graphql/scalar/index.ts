import { GraphQLScalarType } from 'graphql'
import { Kind } from 'graphql/language'
import moment from 'moment-timezone'
import Protobuf from 'protobufjs';
let LongBits = Protobuf.util.LongBits;

export const DateTimeScalar = new GraphQLScalarType ({
  name: 'DateTime',
  description: 'The `DateTime` scalar represents a date and time following the ISO 8601 standard',
  serialize(value) {
    // value sent to the client
    if (value instanceof Date) {
      return moment(value).tz('Asia/Shanghai').format('YYYY-MM-DD HH:mm:ss.SSS')
    }
    return moment(LongBits.from(value.seconds).toNumber() * 1000 + value.nanos / 1000).tz('Asia/Shanghai').format('YYYY-MM-DD HH:mm:ss.SSS')
  },
  parseValue(value) {
    // value from the client
    if (!moment(value).isValid) {
      throw new TypeError(
        `DateTime must be in a recognized RFC2822 or ISO 8601 format ${String(value)}.`
      )
    }

    return moment(value).tz('Asia/Shanghai')
  },
  parseLiteral(ast) {
    if (ast.kind !== Kind.STRING) {
      throw new TypeError(
        `DateTime cannot represent non string type ${String(ast != null ? ast : null)}`
      )
    }

    return moment(ast.value).tz('Asia/Shanghai')
  },
})