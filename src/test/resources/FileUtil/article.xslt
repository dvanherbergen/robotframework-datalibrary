<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml"/>
  <xsl:template match="/">
  	<Authors>
  		<GeneratedBy>Transformation</GeneratedBy>
  		<xsl:apply-templates select="/Article/Authors/Author"/>
  	</Authors>
  </xsl:template>
  <xsl:template match="Author">
    <Author><xsl:value-of select="." /></Author>
  </xsl:template>
</xsl:stylesheet>