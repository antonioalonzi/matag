import React, {Component, Fragment} from 'react'
import {connect} from 'react-redux'
import get from 'lodash/get'
import {bindActionCreators} from 'redux'
import PropTypes from 'prop-types'

class Message extends Component {
  constructor(props) {
    super(props)
    this.handleEscape = this.handleEscape.bind(this)
  }

  handleEscape(event) {
    if (event.key === 'Escape' && this.props.message.closable) {
      this.props.closeMessage()
    }
  }

  componentDidMount() {
    document.addEventListener('keydown', this.handleEscape)
  }

  componentWillUnmount() {
    document.removeEventListener('keydown', this.handleEscape)
  }

  renderCloseButton() {
    if (this.props.message.closable) {
      return <i id='popup-close' onClick={this.props.closeMessage} aria-hidden='true'>X</i>
    }
  }

  getMessage() {
    if (this.props.message.text && this.props.message.text.indexOf('Win!') > 0) {
      return <>{this.props.message.text} <a href={this.props.adminUrl}>Go back to admin.</a></>
    }
    return this.props.message.text
  }

  render() {
    if (this.props.message.text) {
      return (
        <Fragment>
          <div id='modal-container' />
          <div id='popup'>
            { this.renderCloseButton() }
            <p id='message-text'>{this.getMessage()}</p>
          </div>
        </Fragment>
      )
    } else {
      return <Fragment />
    }
  }
}

const closeMessageEvent = () => {
  return {
    type: 'MESSAGE',
    value: {}
  }
}

const mapStateToProps = state => {
  return {
    message: get(state, 'userInterface.message', {}),
    adminUrl: get(state, 'player.gameConfig.adminUrl', '')
  }
}

const mapDispatchToProps = dispatch => {
  return {
    closeMessage: bindActionCreators(closeMessageEvent, dispatch)
  }
}

Message.propTypes = {
  message: PropTypes.object.isRequired,
  closeMessage: PropTypes.func.isRequired
}

export default connect(mapStateToProps, mapDispatchToProps)(Message)
