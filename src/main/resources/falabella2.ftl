<#escape x as (x!)?html>
	<html lang="en">
		<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
				
	
		<!-- a bootstrap css is mandatory -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">	
		
			<style type="text/css">
				body {background-color: rgb(246,246,246); }
				h1 { color: rgb(0,51,102); font-family: helvetica; font-size: 15pt; font-weight: bold; }
				h2 { color: rgb(0,51,102); font-family: helvetica; font-size: 13pt; font-weight: bold; }
				h3 { color: rgb(0,51,102); font-family: helvetica; font-size: 9pt; font-weight: bold; }
				p { color: rgb(0,51,102); font-family: helvetica; font-size: 10pt; font-weight: normal; }
				a { color: rgb(0,51,102); font-family: helvetica; font-size: 10pt; font-weight: normal; }
				a.th { color: rgb(246,246,246); background-color: rgb(102,102,102); font-family: helvetica; font-size: 10pt; font-weight: bold; }
				p.error { color: rgb(255,0,0); font-family: helvetica; font-size: 10pt; font-weight: normal; }
				th.color { color: rgb(246,246,246); background-color: rgb(102,102,102); font-family: helvetica; font-size: 10pt; font-weight: bold; }
				th.no_color { color: rgb(0,51,102); font-family: helvetica; font-size: 10pt; font-weight: bold; }
				td.color { color: rgb(0,51,102); background-color: rgb(255,255,255); font-family: helvetica; font-size: 10pt; font-weight: normal; }
				td.color_pre { color: rgb(0,51,102); background-color: rgb(255,225,225); font-family: helvetica; font-size: 10pt; font-weight: normal; white-space: pre }
				td.no_color { color: rgb(0,51,102); font-family: helvetica; font-size: 10pt; font-weight: normal; }
				td.no_color_pre { color: rgb(0,51,102); font-family: helvetica; font-size: 10pt; font-weight: normal; white-space: pre }
				.methodBody {padding-top: 50px}
			</style>
		
			<title>
				${className(service.webServiceClass)}
			</title>
			
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
		</head>	
		<body>
			<h1>${className(service.webServiceClass)}</h1>
			<#if service.methodStubs??>	
			<table cellspacing="1" cellpadding="1" border="1">
				<tbody>
						<tr valign="top">
							<th align="left" class="color">Index</th>
							<th class="no_color"/>
							<th align="left" class="color">Method</th>				
							<th class="no_color"/>
							<th align="left" class="color">Request(s)</th>
							<th class="no_color"/>
							<th align="left" class="color">Response</th>																											
						</tr>
					<#list service.methodStubs as method>						
						<tr class="no_color">
							<td/>
						</tr>					
						<tr valign="top">
							<td align="left" class="color"><a href="#method${method_index + 1}">${method_index + 1}</a></td>				
							<td class="no_color"/>		
							<td align="left" class="color"><a href="#method${method_index + 1}">${method.methodName}</a></td>
							<td class="no_color"/>
							<td align="left" class="color">
								<#list method.requestStubs as stub>
									  ${stubName(stub)}<br/>
								</#list>
							
							</td>
							<td class="no_color"/>
							<td align="left" class="color">
								<#if method.responseStub??>
									${stubType(method.responseStub)}
								</#if>								 
							</td>								
						</tr>
					</#list>
				</tbody>
			</table>
			
		    <#list service.methodStubs as method>		
		    	<div class="methodBody"> 		    		
		    		<hr/>		
					<a name="method${method_index + 1}"><h2>Method #${method_index + 1} ${method.methodName}</h2></a>
						<#if (method.requestStubs?size > 0)>
							<h3>Request</h3>
							<table cellspacing="1" cellpadding="1" border="1">
								<tbody>
									<tr valign="top">
										<th align="left" class="color">Order</th>
										<th align="left" class="color">Name</th>
										<th align="left" class="color">Cardinality</th>
										<th align="left" class="color">Type</th>
									
										<th align="left" class="color">Required</th>
										
										<th align="left" class="color">Multiple</th>
									
										<#if method.inheritanceInvolved>
											<th align="left" class="color">Scope</th>												
										</#if>
									</tr>									

									<#macro stubRow stub order indence inheritanceInvolved>										
										<tr valign="top">
											<td align="left" class="color">													
												<#list 0..indence as i>&nbsp;</#list>
												${order}													
											</td>
											<td align="left" class="color">
												<#list 0..indence as i>&nbsp;</#list>  
												${stubName(stub)}
											</td>
											<td align="left" class="color">${stub.cardinality}</td>
											<td align="left" class="color">
												<#noescape>
													${stubType(stub)}
												</#noescape>	
											</td>
											
											<td align="left" class="color">${stub.required?string("Y","")}</td>
											
											<td align="left" class="color">${stub.multiOccurs?string("Y","")} </td>																								
											<#if inheritanceInvolved>		
												<td align="left" class="color">
													<#if stub.subTypeOfParentStub??>
														Only for <b>${className(stub.subTypeOfParentStub.name)}</b>											
													</#if>
												</td>
											</#if>
										</tr>								 
										<#list stub.childStubs as childStub>								
											<@stubRow stub=childStub order=order+"."+childStub?counter indence=indence+1 inheritanceInvolved=inheritanceInvolved/>
										</#list>
										
									</#macro>

									<#list method.requestStubs as s>								 		
										<@stubRow stub=s order=2 indence=0 inheritanceInvolved=method.inheritanceInvolved/>
									</#list>
													
								</tbody>
							</table>
						</#if>
			
							<#if method.responseStub??>
								<h3>Response</h3>
								<table cellspacing="1" cellpadding="1" border="1">
									<tbody>
											<tr valign="top">
												<th align="left" class="color">Name</th>
												
												<#if method.inheritanceInvolved>
													<th align="left" class="color">Scope</th>
												</#if>
												<th align="left" class="color">Order</th>
												<th align="left" class="color">Cardinality</th>
												<th align="left" class="color">Type</th>
											
												<th align="left" class="color">Required</th>
											
												<th align="left" class="color">Multiple</th>
											</tr>
											<@stubRow stub=method.responseStub order=2 indence=0 inheritanceInvolved=method.inheritanceInvolved/>							
									</tbody>
								</table>					
							</#if>
							
							<#if method.inheritanceInvolved>
								<h3>Type Hierarchy</h3>
								
								<#macro typeRow typeTree indence>
											<tr class="no_color">
												<td/>
											</tr>					
											<tr valign="top">
												<td align="left" class="color">
													<#list 0..indence as i>
													  	&nbsp;&nbsp;
													</#list>  
													${className(typeTree.type.name)}
												</td>																								
											</tr>								 
											<#list typeTree.children as childTree>									
												<@typeRow typeTree=childTree indence=indence+1/>
											</#list>								
			
								</#macro>  									
								
								<table cellspacing="1" cellpadding="1" border="1">
									<tbody>									
											<#list method.stubTypeTreeRepository.allTrees as typeTree>
												<#if !typeTree.parent??>
													<tr>								
														<@typeRow typeTree=typeTree indence=0/>
													</tr>											
												</#if>		
	
											</#list>
									</tbody>
								</table>								
							</#if>						
						 
					</div>				
			</#list>
		   </#if>
		</body>
	</html>  
</#escape>