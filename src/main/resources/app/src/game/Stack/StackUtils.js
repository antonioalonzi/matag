export default class StackUtils {
  static isStackEmpty(state) {
    return !state.stack || state.stack.items.length === 0
  }

  static isACastedCard(cardInstance) {
    return cardInstance.triggeredAbilities.length === 0
  }
}